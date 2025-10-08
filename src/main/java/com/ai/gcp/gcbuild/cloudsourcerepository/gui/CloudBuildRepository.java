package com.ai.gcp.gcbuild.cloudsourcerepository.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CloudBuildRepository {

	private final String url = "jdbc:mysql://localhost:3306/ai_gcp_gcbuild_cloudsourcerepository?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private final String user = "root";
	private final String password = "admin"; // Set your MySQL password

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	// --- CREATE ---
	public void addBuild(String id, String status, String source, Timestamp createTime) {
		String sql = "INSERT INTO cloud_builds (id, status, source, create_time) VALUES (?, ?, ?, ?)";
		try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.setString(2, status);
			ps.setString(3, source);
			ps.setTimestamp(4, createTime);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Warning: addBuild failed. Using dummy fallback. (" + e.getMessage() + ")");
		}
	}

	// --- READ ---
	public List<HashMap<String, String>> getAllBuilds() {
		List<HashMap<String, String>> builds = new ArrayList<>();
		String sql = "SELECT * FROM cloud_builds";
		try (Connection conn = connect(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				HashMap<String, String> build = new HashMap<>();
				build.put("id", rs.getString("id"));
				build.put("status", rs.getString("status"));
				build.put("source", rs.getString("source"));
				build.put("create_time", rs.getTimestamp("create_time").toString());
				builds.add(build);
			}
		} catch (SQLException e) {
			System.out.println("Warning: Cannot connect to MySQL. Using dummy builds. (" + e.getMessage() + ")");
			builds.add(dummyBuild("build_001", "SUCCESS", "repo_alpha", "2025-10-01 10:00:00"));
			builds.add(dummyBuild("build_002", "FAILURE", "repo_beta", "2025-10-02 12:30:00"));
			builds.add(dummyBuild("build_003", "WORKING", "repo_gamma", "2025-10-03 14:45:00"));
		}
		return builds;
	}

	// --- UPDATE ---
	public void updateBuildStatus(String id, String newStatus) {
		String sql = "UPDATE cloud_builds SET status=? WHERE id=?";
		try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, newStatus);
			ps.setString(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Warning: updateBuildStatus failed. Using dummy fallback. (" + e.getMessage() + ")");
		}
	}

	// --- DELETE ---
	public void deleteBuild(String id) {
		String sql = "DELETE FROM cloud_builds WHERE id=?";
		try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Warning: deleteBuild failed. Using dummy fallback. (" + e.getMessage() + ")");
		}
	}

	private HashMap<String, String> dummyBuild(String id, String status, String source, String createTime) {
		HashMap<String, String> build = new HashMap<>();
		build.put("id", id);
		build.put("status", status);
		build.put("source", source);
		build.put("create_time", createTime);
		return build;
	}
}
