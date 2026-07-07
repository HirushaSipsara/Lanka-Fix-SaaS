# LankaFix AWS ECS Fargate & Terraform Deployment Guide

This guide details the complete process of preparing, dockerizing, provisioning, and deploying the LankaFix SaaS application on AWS using Terraform (IaC) and GitHub Actions (CI/CD).

---

## 📖 Table of Contents
1. [Architectural Blueprint](#1-architectural-blueprint)
2. [Codebase Modifications](#2-codebase-modifications)
3. [Dockerization (Multi-stage Containers)](#3-dockerization-multi-stage-containers)
4. [Infrastructure as Code (Terraform Setup)](#4-infrastructure-as-code-terraform-setup)
5. [The AWS Deployment Playbook](#5-the-aws-deployment-playbook)
6. [CI/CD Configuration (GitHub Actions)](#6-cicd-configuration-github-actions)
7. [Troubleshooting Guide (Real-world Failures)](#7-troubleshooting-guide-real-world-failures)

---

## 1. Architectural Blueprint

The LankaFix deployment leverages serverless container orchestration and network security isolation.

```
                                  [ INTERNET ]
                                       │
                                       ▼ (HTTP port 80)
                       [ AWS Application Load Balancer ]
                                 │          │
                     ┌───────────┘          └───────────┐
      /api/* (Port 8081) │                              │ /* (Port 80)
                         ▼                              ▼
             [ ECS Fargate Task ]              [ ECS Fargate Task ]
             (Spring Boot Backend)             (React Nginx Frontend)
                         │
                         ▼ (Port 5432)
              [ RDS PostgreSQL Instance ]
              (Private Subnet Isolation)
```

### Components & How They Work:
1. **VPC (`vpc.tf`):** Defines an isolated virtual network (`10.0.0.0/16`) divided into:
   - **Public Subnets (`10.0.0.0/24`, `10.0.1.0/24`):** House the ALB and ECS tasks. These have direct routes to the internet via an Internet Gateway.
   - **Private Subnets (`10.0.2.0/24`, `10.0.3.0/24`):** House the RDS database instance. No direct access from the internet.
2. **Security Groups (`security_groups.tf`):** Firewalls controlling traffic:
   - **ALB Security Group:** Allows port 80 (HTTP) traffic from the public internet.
   - **ECS Tasks Security Group:** Only allows incoming traffic on ports `8081` (backend) and `80` (frontend) originating from the ALB Security Group.
   - **RDS Security Group:** Only allows incoming traffic on port `5432` originating from the ECS Tasks Security Group.
3. **Application Load Balancer (`alb.tf`):** Evaluates requests:
   - `/api/*` requests go to `lankafix-backend-tg` (port 8081).
   - All other requests (`/*`) go to `lankafix-frontend-tg` (port 80).
4. **ECS Fargate (`ecs.tf`):** Runs serverless Docker containers. No EC2 instances to manage. CPU and Memory are allocated at the task level.
5. **RDS PostgreSQL (`db.tf`):** A managed SQL database deployed in private subnets, configured for Free-Tier eligibility (`db.t3.micro`).

---

## 2. Codebase Modifications

Before containerization, the local codebase had hardcoded addresses and lacked cloud observability.

### 2.1 Decoupled CORS Configuration
All `@CrossOrigin` annotations were removed from individual controllers to avoid conflicting with the ALB. CORS is now managed centrally.
- **Affected File:** `backend/WedaLK/demo/src/main/resources/application.properties`
- **Configuration:**
  ```properties
  # Centralized CORS
  app.cors.allowed-origins=${CORS_ALLOWED_ORIGINS:http://localhost:3000}
  ```
- **Java Configuration:** Configures origins dynamically based on the injected environment variable.

### 2.2 Observability & Monitoring Integration
Spring Boot Actuator was configured to expose Prometheus-formatted metrics on a public endpoint for scraping.
- **Affected File:** `backend/WedaLK/demo/pom.xml`
  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-registry-prometheus</artifactId>
  </dependency>
  ```
- **Endpoint Configuration (`application.properties`):**
  ```properties
  management.endpoints.web.exposure.include=health,prometheus
  management.endpoint.health.show-details=always
  management.metrics.export.prometheus.enabled=true
  ```
- **Security Override (`SecurityConfig.java`):** Configured to allow Prometheus queries without authentication:
  ```java
  .requestMatchers("/actuator/**").permitAll()
  ```

---

## 3. Dockerization (Multi-stage Containers)

Multi-stage builds are critical for production to keep image sizes small and minimize security vulnerability surfaces.

### 3.1 Backend Dockerfile
- **Path:** `backend/WedaLK/demo/Dockerfile`
```dockerfile
# Stage 1: Build the JAR
FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Minimal runtime JRE Alpine
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 3.2 Frontend Dockerfile
- **Path:** `frontend/Dockerfile`
```dockerfile
# Stage 1: Build production React assets
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
ARG REACT_APP_API_URL
ENV REACT_APP_API_URL=$REACT_APP_API_URL
RUN npm run build

# Stage 2: Serve using Nginx
FROM nginx:1.25-alpine
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

---

## 4. Infrastructure as Code (Terraform Setup)

### Configuration Structure (`terraform/`)
- `providers.tf`: Selects the AWS provider and pinning versions.
- `variables.tf`: Declares input parameters.
- `vpc.tf`: Provisions networking (VPC, Subnets, Gateway, Routes).
- `security_groups.tf`: Restricts inbound/outbound traffic rules.
- `ecr.tf`: Sets up private container registries (Nginx and JVM).
- `db.tf`: Provisions RDS PostgreSQL instance.
- `alb.tf`: Provisions the Application Load Balancer and Routing rules.
- `ecs.tf`: Sets up the Fargate Cluster, task execution IAM roles, logs, and service counts.
- `outputs.tf`: Prints out ALB DNS name and RDS endpoints.
- `terraform.tfvars`: File for secret variables (database password). **Excluded from Git**.

---

## 5. The AWS Deployment Playbook

Use this exact sequence of commands to deploy the architecture from scratch.

### Step 1: Configure Credentials
Authenticate with your AWS account:
```bash
aws configure
```

### Step 2: Initialize & Bootstrap ECR repositories
Run Terraform only targeting ECR to establish the registry targets:
```bash
cd terraform
terraform init
terraform apply \
  -target=aws_ecr_repository.backend \
  -target=aws_ecr_repository.frontend \
  -var="db_password=placeholder" \
  -auto-approve
```

### Step 3: Log in to ECR & Push Initial Images
Build and push images using your AWS Account ID (replace `<ACCOUNT_ID>` with your ID):
```bash
# ECR Login
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com

# Backend Build & Push
docker build -t lankafix-backend ../backend/WedaLK/demo
docker tag lankafix-backend:latest <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/lankafix-backend:latest
docker push <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/lankafix-backend:latest

# Frontend Build & Push
docker build -t lankafix-frontend ../frontend
docker tag lankafix-frontend:latest <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/lankafix-frontend:latest
docker push <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/lankafix-frontend:latest
```

### Step 4: Create Secrets File
Create a `terraform.tfvars` inside the `terraform/` folder:
```hcl
db_password = "YourSecurePassword123!" # Must not contain '@', '/', or '"'
```

### Step 5: Execute Full Apply
Provision the rest of the stack (VPC, RDS Database, ALB, and ECS Tasks):
```bash
terraform apply -auto-approve
```
Once complete, capture the `alb_dns_name` output value.

---

## 6. CI/CD Configuration (GitHub Actions)

The workflow (`.github/workflows/deploy.yml`) is triggered on pushes to the `main` branch.

### Secrets Setup on GitHub:
Go to your repository settings -> **Secrets and variables** -> **Actions** -> Add:
- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
- `AWS_REGION` (`us-east-1`)

### Deployment Pipeline Workflow:

The workflow contains three stages:

1. **Backend Checks (CI Job):**
   - Checks out repository.
   - Sets up JDK 17 with Maven caching.
   - Makes the Maven wrapper executable and runs Maven test suite (`./mvnw test`).
2. **Frontend Checks (CI Job):**
   - Checks out repository.
   - Sets up Node.js 18 with npm caching.
   - Installs dependencies using clean-install (`npm ci`).
   - Runs ESLint code style analysis (`npm run lint`).
   - Runs React frontend unit test suite (`npm test -- --watchAll=false --passWithNoTests`).
3. **Build, Push, and Deploy (CD Job):**
   - Runs **only** if both `backend-checks` and `frontend-checks` jobs complete successfully (`needs` requirement).
   - Authenticates to AWS using GitHub Secrets.
   - Builds backend and frontend Docker containers (injecting relative `/api` path variable).
   - Tags the images with the git commit SHA and `latest`.
   - Pushes both images to their respective AWS ECR repositories.
   - Dynamically downloads active ECS task definition JSONs from AWS.
   - Updates the task definitions with the new image tags and registers new task definition revisions.
   - Performs a zero-downtime rolling update on the ECS Fargate cluster.

---

## 7. Troubleshooting Guide (Real-world Failures)

These are the exact issues resolved during implementation:

### 7.1 "InvalidParameterValue: The parameter MasterUserPassword is not a valid password"
- **Cause:** The RDS password contained a `@` character. Connection strings format user passwords using `postgresql://user:password@host/db`. Having a `@` inside the password breaks URI parsing.
- **Solution:** Keep alphanumeric characters and safe symbols like `#` or `!`. Avoid `/`, `@`, and `"`.

### 7.2 "InvalidParameterCombination: Cannot find version 15.12 for postgres"
- **Cause:** AWS RDS supports only specific minor engine versions. If you supply an invalid or unsupported version, AWS API rejects the call.
- **Solution:** Use verified stable minor versions (e.g., `15.7` instead of guess versions like `15.12`). Run `aws rds describe-db-engine-versions` to list valid choices.

### 7.3 "ECR Repository not empty, RepositoryNotEmptyException"
- **Cause:** AWS blocks Terraform from destroying ECR repositories that contain active images to prevent accidental code/data loss.
- **Solution:** 
  1. Add `force_delete = true` inside `aws_ecr_repository` resource declarations.
  2. Alternatively, manually wipe all tagged/untagged images in the repository using the AWS CLI before running `terraform destroy`:
  ```bash
  aws ecr batch-delete-image --repository-name lankafix-backend --image-ids imageTag=latest
  aws ecr batch-delete-image --repository-name lankafix-backend --image-ids "$(aws ecr list-images --repository-name lankafix-backend --query 'imageIds[*]' --output json)"
  ```

### 7.4 "File exceeds GitHub's file size limit of 100.00 MB"
- **Cause:** Running `terraform init` downloads massive provider binaries (e.g., `terraform-provider-aws_v5` is 600MB+) to `terraform/.terraform/`. If committed, git records them in history. Adding it to `.gitignore` afterwards *will not* remove it from the tracking history.
- **Solution:** Soft-reset the commits to keep file modifications but remove the binaries from the staging index:
  ```bash
  git reset --soft <last_healthy_commit_sha>
  git restore --staged terraform/.terraform/ terraform/tfplan
  git commit -m "clean commit"
  git push origin main
  ```
  *Make sure `.gitignore` correctly ignores `terraform/.terraform/`.*
