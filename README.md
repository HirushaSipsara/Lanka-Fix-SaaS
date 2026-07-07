# LankaFix SaaS - Sri Lankan Services Marketplace

LankaFix (WedaLK) is a full-stack, cloud-native services marketplace tailored for Sri Lanka. The platform connects service seekers with skilled workers (e.g., plumbers, electricians, developers) using secure role-based access, quotation/bidding mechanisms, and a modern containerized infrastructure.

---

## 🏗️ Architecture Overview

The application is deployed on AWS using a highly secure, scalable, and isolated architecture defined via Terraform (Infrastructure as Code):

```
                        [ Internet ]
                             │
                             ▼
                [ Application Load Balancer ]
                 ├── /api/* ──► [ ECS Fargate: Spring Boot Backend ]
                 └── /*     ──► [ ECS Fargate: React Nginx Frontend ]
                                            │
                                            ▼
                               [ RDS PostgreSQL Database ]
                               (Private Subnet Isolation)
```

- **VPC Isolation:** Public subnets house the ALB and ECS Tasks (assigning public IPs dynamically), while the RDS PostgreSQL database is isolated in a private subnet group.
- **Path-Based Routing:** The Application Load Balancer routes traffic to `/api/*` to the Spring Boot backend, and all other traffic (`/*`) to the React Nginx server.
- **Zero-Downtime CI/CD:** GitHub Actions automatically builds and pushes Docker images to ECR, updating the ECS services seamlessly on merge to `main`.

---

## 🛠️ Technology Stack

- **Frontend:** React 19, Tailwind CSS, Axios, React Router 7, Nginx (production server)
- **Backend:** Spring Boot 3.2 (Java 17), Spring Security + JWT, Spring Data JPA, Hibernate, Actuator + Micrometer
- **Database:** PostgreSQL (RDS / Local)
- **Infrastructure:** Docker, Terraform, AWS (VPC, ECS Fargate, ECR, RDS, ALB, CloudWatch)
- **CI/CD:** GitHub Actions

---

## 🚀 Local Development Setup

### 1. Prerequisite Checklist
- Docker & Docker Compose
- Java 17 JDK & Maven (optional, if running backend bare-metal)
- Node.js & npm (optional, if running frontend bare-metal)

### 2. Running with Docker Compose (Recommended)
This runs the full stack (Frontend, Backend, PostgreSQL) locally with one command:
```bash
docker compose up --build
```
- **Frontend:** `http://localhost:3000`
- **Backend API:** `http://localhost:8081`
- **Prometheus Metrics:** `http://localhost:8081/actuator/prometheus`

---

## ☁️ AWS Deployment Playbook (Terraform IaC)

Follow these steps to deploy the entire stack to AWS and activate the CI/CD pipeline.

### Step 1: Configure AWS CLI
Ensure your terminal session is authenticated to your AWS account:
```bash
aws configure
```

### Step 2: Bootstrap the Container Registry (ECR)
Terraform needs ECR to exist so you can push your Docker images before building the rest of the infrastructure:
```bash
cd terraform
terraform init
terraform apply -target=aws_ecr_repository.backend -target=aws_ecr_repository.frontend -auto-approve
```

### Step 3: Build & Push Initial Docker Images
Log in to AWS ECR and push your Docker containers (replace `<ACCOUNT_ID>` with your AWS Account ID):
```bash
# ECR login
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com

# Build & Push Backend
docker build -t lankafix-backend ../backend/WedaLK/demo
docker tag lankafix-backend:latest <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/lankafix-backend:latest
docker push <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/lankafix-backend:latest

# Build & Push Frontend
docker build -t lankafix-frontend ../frontend
docker tag lankafix-frontend:latest <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/lankafix-frontend:latest
docker push <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/lankafix-frontend:latest
```

### Step 4: Run Full Apply
Provisions the VPC, RDS database instance, ALB, ECS Cluster, and Fargate Services:
```bash
# Add your DB password to terraform.tfvars first, then run:
terraform apply -auto-approve
```
When complete, Terraform will output the **ALB DNS Name** (e.g., `lankafix-alb-2089176840.us-east-1.elb.amazonaws.com`). This is your live application URL!

---

## 🔄 GitHub Actions CI/CD Pipeline Setup

To enable automated deployments on every `git push`:

1. Go to your GitHub repository → **Settings** → **Secrets and variables** → **Actions**.
2. Add the following repository secrets:
   - `AWS_ACCESS_KEY_ID`: Your IAM user access key.
   - `AWS_SECRET_ACCESS_KEY`: Your IAM user secret access key.
   - `AWS_REGION`: `us-east-1`
3. Push changes to the `main` branch. GitHub Actions will trigger, build the images, push them to ECR, and automatically update your ECS Fargate services.

---

## 🛑 Clean Up (Stop Charges)

To completely destroy all resources on AWS and avoid incurring charges when you are done demonstrating the project:
```bash
cd terraform
terraform destroy -auto-approve
```
