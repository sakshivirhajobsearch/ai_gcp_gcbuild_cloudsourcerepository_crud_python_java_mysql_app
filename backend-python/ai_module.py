# ai_module.py

from gcp_build import list_builds
from gcp_source_repo import list_repos

def get_ai_overview():
    """
    Returns AI analysis overview for builds and repos (dummy data).
    """
    builds = list_builds()
    repos = list_repos()

    overview = "=== AI Overview ===\n\nBuilds:\n"
    for b in builds:
        status_score = 0.9 if b["status"] == "SUCCESS" else 0.3
        overview += f"{b['id']} | Status: {b['status']} | AI Success Prob: {status_score}\n"

    overview += "\nRepositories:\n"
    for r in repos:
        risk_score = 0.2  # Dummy risk score
        overview += f"{r['name']} | Risk Score: {risk_score}\n"

    return overview

# Test
if __name__ == "__main__":
    print(get_ai_overview())
