from flask import Flask, Response
from datetime import datetime

app = Flask(__name__)

# Dummy data
builds = [
    {"id": "build_001", "status": "SUCCESS", "ai_prob": 0.9},
    {"id": "build_002", "status": "FAILURE", "ai_prob": 0.3},
    {"id": "build_003", "status": "WORKING", "ai_prob": 0.3},
]

repos = [
    {"name": "projects/dummy-project/repos/repo_alpha", "risk": 0.2},
    {"name": "projects/dummy-project/repos/repo_beta", "risk": 0.2},
    {"name": "projects/dummy-project/repos/repo_gamma", "risk": 0.2},
]

def format_ai_overview_table():
    # Builds Table
    output = "=== AI Overview: Builds ===\n"
    output += f"{'Build ID':<12} | {'Status':<8} | {'AI Success Prob':<15}\n"
    output += "-" * 40 + "\n"
    for b in builds:
        output += f"{b['id']:<12} | {b['status']:<8} | {b['ai_prob']:<15.2f}\n"

    # Repos Table
    output += "\n=== AI Overview: Repositories ===\n"
    output += f"{'Repository':<40} | {'Risk':<5}\n"
    output += "-" * 50 + "\n"
    for r in repos:
        output += f"{r['name']:<40} | {r['risk']:<5.2f}\n"

    return output

def format_dashboard_table():
    total_builds = len(builds)
    total_repos = len(repos)

    output = "=== DASHBOARD SUMMARY ===\n"
    output += f"Total Builds: {total_builds} | Total Repositories: {total_repos}\n\n"

    # Recent Builds Table
    output += "Recent Builds:\n"
    output += f"{'Build ID':<12} | {'Status':<6} | {'AI Prob':<7}\n"
    output += "-" * 30 + "\n"
    for b in builds:
        icon = "✅" if b["status"] == "SUCCESS" else "❌" if b["status"] == "FAILURE" else "⏳"
        output += f"{b['id']:<12} | {icon:<6} | {b['ai_prob']:<7.2f}\n"

    # Repository Risk Overview
    output += "\nRepository Risk Overview:\n"
    output += f"{'Repository':<40} | {'Risk Level':<10}\n"
    output += "-" * 55 + "\n"
    for r in repos:
        risk_level = "LOW" if r["risk"] < 0.5 else "MEDIUM" if r["risk"] < 0.8 else "HIGH"
        output += f"{r['name']:<40} | {r['risk']:<5.2f} ({risk_level})\n"

    return output

@app.route("/")
def home():
    return "Welcome! Use /ai/overview or /dashboard to view data."

@app.route('/ai/overview')
def ai_overview():
    return Response(format_ai_overview_table(), mimetype='text/plain')

@app.route('/dashboard')
def dashboard():
    return Response(format_dashboard_table(), mimetype='text/plain')

if __name__ == '__main__':
    app.run(debug=True)
