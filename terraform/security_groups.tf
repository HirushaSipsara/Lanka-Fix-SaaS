# ALB Security Group
resource "aws_security_group" "alb" {
  name        = "lankafix-alb-sg"
  description = "Access to the load balancer"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "lankafix-alb-sg"
    Environment = var.environment
  }
}

# ECS Security Group (Frontend and Backend)
resource "aws_security_group" "ecs_tasks" {
  name        = "lankafix-ecs-tasks-sg"
  description = "Control access to ECS containers"
  vpc_id      = aws_vpc.main.id

  # Inbound HTTP from ALB to Frontend (port 80)
  ingress {
    from_port       = 80
    to_port         = 80
    protocol        = "tcp"
    security_groups = [aws_security_group.alb.id]
  }

  # Inbound HTTP from ALB to Backend (port 8081)
  ingress {
    from_port       = 8081
    to_port         = 8081
    protocol        = "tcp"
    security_groups = [aws_security_group.alb.id]
  }

  # Outbound to any address (required to download dependencies/images, connect to DB)
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "lankafix-ecs-tasks-sg"
    Environment = var.environment
  }
}

# RDS Database Security Group
resource "aws_security_group" "db" {
  name        = "lankafix-db-sg"
  description = "Allows ECS tasks connection to RDS database"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [aws_security_group.ecs_tasks.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "lankafix-db-sg"
    Environment = var.environment
  }
}
