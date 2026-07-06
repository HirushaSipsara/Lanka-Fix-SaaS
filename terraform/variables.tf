variable "aws_region" {
  type        = string
  default     = "us-east-1"
  description = "AWS region to deploy resources"
}

variable "environment" {
  type        = string
  default     = "production"
  description = "Deployment environment name"
}

variable "db_password" {
  type        = string
  sensitive   = true
  description = "Database master password for RDS"
}

variable "jwt_secret" {
  type        = string
  default     = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"
  description = "JWT Secret for application security"
}

variable "gemini_api_key" {
  type        = string
  default     = ""
  sensitive   = true
  description = "API Key for Google Gemini integrations"
}
