package com.ai.gcp.gcbuild.cloudsource.repository;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.ai.gcp.gcbuild.cloudsourcerepository.gui.CloudBuildRepository;
import com.ai.gcp.gcbuild.cloudsourcerepository.gui.SourceRepoRepository;

public class UnifiedGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextArea outputArea;
	private CloudBuildRepository buildRepo;
	private SourceRepoRepository repoRepo;

	public UnifiedGUI() {
		buildRepo = new CloudBuildRepository();
		repoRepo = new SourceRepoRepository();

		setTitle("GCP Cloud Build & Source Repo CRUD with AI");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Buttons
		JButton listBuildBtn = new JButton("List Builds");
		JButton addBuildBtn = new JButton("Add Build");
		JButton deleteBuildBtn = new JButton("Delete Build");
		JButton updateBuildBtn = new JButton("Update Build Status");

		JButton listRepoBtn = new JButton("List Repos");
		JButton addRepoBtn = new JButton("Add Repo");
		JButton updateRepoBtn = new JButton("Update Repo URL");
		JButton deleteRepoBtn = new JButton("Delete Repo");

		JButton aiBtn = new JButton("AI Overview");

		// Output area
		outputArea = new JTextArea();
		outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(outputArea);

		// Button panel
		JPanel panel = new JPanel(new GridLayout(3, 4, 5, 5));
		panel.add(listBuildBtn);
		panel.add(addBuildBtn);
		panel.add(deleteBuildBtn);
		panel.add(updateBuildBtn);
		panel.add(listRepoBtn);
		panel.add(addRepoBtn);
		panel.add(updateRepoBtn);
		panel.add(deleteRepoBtn);
		panel.add(aiBtn);

		add(panel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		// --- Action Listeners ---

		listBuildBtn.addActionListener(e -> {
			List<HashMap<String, String>> builds = buildRepo.getAllBuilds();
			List<String> display = builds.stream().map(b -> b.get("id") + " | Status: " + b.get("status")
					+ " | Source: " + b.get("source") + " | Created: " + b.get("create_time"))
					.collect(Collectors.toList());
			outputArea.setText(String.join("\n", display));
		});

		addBuildBtn.addActionListener(e -> {
			String id = JOptionPane.showInputDialog("Enter Build ID:");
			String status = JOptionPane.showInputDialog("Enter Status (SUCCESS/FAILURE):");
			String source = JOptionPane.showInputDialog("Enter Source Repo:");
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			buildRepo.addBuild(id, status, source, ts);
			outputArea.setText("Build added successfully!");
		});

		updateBuildBtn.addActionListener(e -> {
			String id = JOptionPane.showInputDialog("Enter Build ID to update:");
			String status = JOptionPane.showInputDialog("Enter new Status:");
			buildRepo.updateBuildStatus(id, status);
			outputArea.setText("Build updated successfully!");
		});

		deleteBuildBtn.addActionListener(e -> {
			String id = JOptionPane.showInputDialog("Enter Build ID to delete:");
			buildRepo.deleteBuild(id);
			outputArea.setText("Build deleted successfully!");
		});

		listRepoBtn.addActionListener(e -> {
			List<HashMap<String, String>> repos = repoRepo.getAllRepos();
			List<String> display = repos.stream().map(r -> r.get("name") + " | URL: " + r.get("url"))
					.collect(Collectors.toList());
			outputArea.setText(String.join("\n", display));
		});

		addRepoBtn.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("Enter Repo Name:");
			String url = JOptionPane.showInputDialog("Enter Repo URL:");
			repoRepo.addRepo(name, url);
			outputArea.setText("Repository added successfully!");
		});

		updateRepoBtn.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("Enter Repo Name to update:");
			String url = JOptionPane.showInputDialog("Enter new Repo URL:");
			repoRepo.updateRepoUrl(name, url);
			outputArea.setText("Repository updated successfully!");
		});

		deleteRepoBtn.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("Enter Repo Name to delete:");
			repoRepo.deleteRepo(name);
			outputArea.setText("Repository deleted successfully!");
		});

		aiBtn.addActionListener(e -> {
			try {
				String apiUrl = "http://127.0.0.1:5000/ai/overview";
				java.net.URL url = new java.net.URL(apiUrl);
				java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				java.io.BufferedReader in = new java.io.BufferedReader(
						new java.io.InputStreamReader(con.getInputStream()));
				StringBuilder content = new StringBuilder();
				String line;
				while ((line = in.readLine()) != null)
					content.append(line).append("\n");
				in.close();
				con.disconnect();
				outputArea.setText(content.toString());
			} catch (Exception ex) {
				outputArea.setText("Error fetching AI Overview from http://127.0.0.1:5000/:\n" + ex.getMessage());
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new UnifiedGUI().setVisible(true));
	}
}
