# gcp_source_repo.py

from config import GCP_PROJECT_ID

def list_repos(project_id=None):
    """
    List Cloud Source Repositories. Returns dummy repos for offline/local testing.
    """
    if project_id is None:
        project_id = GCP_PROJECT_ID

    if project_id == "dummy-project":
        print("Using dummy repositories because project is dummy.")
        return [
            {"name": "projects/dummy-project/repos/repo_alpha", "url": "https://source.developers.google.com/p/project/r/repo_alpha"},
            {"name": "projects/dummy-project/repos/repo_beta", "url": "https://source.developers.google.com/p/project/r/repo_beta"},
            {"name": "projects/dummy-project/repos/repo_gamma", "url": "https://source.developers.google.com/p/project/r/repo_gamma"},
        ]

def create_repo(repo_name, project_id=None):
    if project_id is None:
        project_id = GCP_PROJECT_ID
    return {"name": f"projects/dummy-project/repos/{repo_name}", "url": f"https://source.developers.google.com/p/project/r/{repo_name}"}

def delete_repo(repo_name, project_id=None):
    if project_id is None:
        project_id = GCP_PROJECT_ID
    return {"status": "deleted", "name": repo_name}

# Test
if __name__ == "__main__":
    for repo in list_repos():
        print(repo)
