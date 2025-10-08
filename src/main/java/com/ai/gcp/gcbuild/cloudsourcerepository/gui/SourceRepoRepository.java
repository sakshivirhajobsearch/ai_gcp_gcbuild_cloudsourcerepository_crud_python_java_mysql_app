package com.ai.gcp.gcbuild.cloudsourcerepository.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SourceRepoRepository {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/ai_gcp_gcbuild_cloudsourcerepository?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";

	public List<HashMap<String, String>> getAllRepos() {
		List<HashMap<String, String>> repos = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT name, url FROM source_repos")) {

			while (rs.next()) {
				HashMap<String, String> repo = new HashMap<>();
				repo.put("name", rs.getString("name"));
				repo.put("url", rs.getString("url"));
				repos.add(repo);
			}

		} catch (SQLException e) {
			System.out.println("Warning: Cannot connect to MySQL. Using dummy repositories. (" + e.getMessage() + ")");
			repos.add(dummyRepo("repo_alpha"));
			repos.add(dummyRepo("repo_beta"));
			repos.add(dummyRepo("repo_gamma"));
		}
		return repos;
	}

	public HashMap<String, String> addRepo(String repoName, String repoUrl) {
		System.out.println("Warning: addRepo not connected to MySQL. Using dummy response.");
		HashMap<String, String> repo = new HashMap<>();
		repo.put("name", "projects/dummy-project/repos/" + repoName);
		repo.put("url", repoUrl != null ? repoUrl : "https://source.developers.google.com/p/project/r/" + repoName);
		return repo;
	}

	public HashMap<String, String> updateRepoUrl(String repoName, String newUrl) {
		System.out.println("Warning: updateRepoUrl not connected to MySQL. Using dummy response.");
		HashMap<String, String> repo = new HashMap<>();
		repo.put("name", "projects/dummy-project/repos/" + repoName);
		repo.put("url", newUrl);
		return repo;
	}

	public HashMap<String, String> deleteRepo(String repoName) {
		System.out.println("Warning: deleteRepo not connected to MySQL. Using dummy response.");
		HashMap<String, String> repo = new HashMap<>();
		repo.put("status", "deleted");
		repo.put("name", repoName);
		return repo;
	}

	private HashMap<String, String> dummyRepo(String repoName) {
		HashMap<String, String> repo = new HashMap<>();
		repo.put("name", "projects/dummy-project/repos/" + repoName);
		repo.put("url", "https://source.developers.google.com/p/project/r/" + repoName);
		return repo;
	}
}
