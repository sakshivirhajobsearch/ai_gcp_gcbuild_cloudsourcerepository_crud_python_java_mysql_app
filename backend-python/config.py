import os

# MySQL config
MYSQL_HOST = os.getenv("MYSQL_HOST", "localhost")
MYSQL_USER = os.getenv("MYSQL_USER", "root")
MYSQL_PASSWORD = os.getenv("MYSQL_PASSWORD", "admin")
MYSQL_DB = os.getenv("MYSQL_DB", "ai_gcp_gcbuild_cloudsourcerepository")

# Google Cloud
GCP_PROJECT_ID = os.getenv("GCP_PROJECT_ID", "YOUR_PROJECT_ID")
GCP_CREDENTIALS = os.getenv("GOOGLE_APPLICATION_CREDENTIALS", "path/to/service_account.json")

# Use a real GCP project ID if available. Otherwise, keep a dummy value
GCP_PROJECT_ID = "dummy-project"