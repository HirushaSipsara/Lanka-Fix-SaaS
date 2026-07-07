# RDS DB Subnet Group
resource "aws_db_subnet_group" "main" {
  name       = "lankafix-db-subnet-group"
  subnet_ids = aws_subnet.private[*].id

  tags = {
    Name        = "lankafix-db-subnet-group"
    Environment = var.environment
  }
}

# RDS PostgreSQL Instance
resource "aws_db_instance" "postgres" {
  identifier             = "lankafix-db"
  allocated_storage      = 20
  max_allocated_storage  = 100
  engine                 = "postgres"
  engine_version         = "15.7"
  instance_class         = "db.t3.micro" # Free Tier eligible
  db_name                = "lankafix"
  username               = "postgres"
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.db.id]
  skip_final_snapshot    = true

  tags = {
    Name        = "lankafix-db-instance"
    Environment = var.environment
  }
}
