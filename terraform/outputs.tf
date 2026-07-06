output "alb_dns_name" {
  value       = aws_lb.main.dns_name
  description = "The public DNS name of the Application Load Balancer."
}

output "ecr_backend_url" {
  value       = aws_ecr_repository.backend.repository_url
  description = "The URL of the ECR repository for the backend."
}

output "ecr_frontend_url" {
  value       = aws_ecr_repository.frontend.repository_url
  description = "The URL of the ECR repository for the frontend."
}

output "rds_endpoint" {
  value       = aws_db_instance.postgres.endpoint
  description = "The database endpoint."
}
