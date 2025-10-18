# gcp_build.py

from config import GCP_PROJECT_ID

def list_builds():
    """
    List Cloud Builds. Returns dummy builds for offline/local testing.
    """
    if GCP_PROJECT_ID == "dummy-project":
        print("Using dummy builds because project is dummy.")
        return [
            {"id": "build_001", "status": "SUCCESS", "source": "repo_alpha", "create_time": "2025-10-01T10:00:00"},
            {"id": "build_002", "status": "FAILURE", "source": "repo_beta", "create_time": "2025-10-02T12:30:00"},
            {"id": "build_003", "status": "WORKING", "source": "repo_gamma", "create_time": "2025-10-03T14:45:00"},
        ]

# Test
if __name__ == "__main__":
    for build in list_builds():
        print(build)
