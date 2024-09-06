package Project;

import java.awt.Dimension;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class FileManager {

    public final String url = "jdbc:sqlite:tasksDB";
    private List<Tasks> taskList = new ArrayList<>();
    public final Path folderPath = Paths.get("Saved_Tasks");

    //Displaying file properties like size and creation
    public void displayFileProperties(String filePath) {
        Path path = folderPath.resolve(filePath);
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            JOptionPane.showMessageDialog(null,
                    "File Size: " + attrs.size() + " bytes\n"
                    + "Creation Time: " + attrs.creationTime() + "\n"
                    + "Last Modified Time: " + attrs.lastModifiedTime());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Check if the folder exists or not. Create folder if it doesn't exist
    public void folderExists() {
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
                JOptionPane.showMessageDialog(null, "Folder 'Saved_Tasks' created successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to create the folder 'Saved_Tasks': " + e.getMessage());
            }
        }
    }

    // Save a specific task to a file
    public void saveTaskToFile(String taskId, String filePath) {
        String sql = "SELECT * FROM TasksTbl WHERE ID = " + taskId;

        try {
            if (!Files.exists(folderPath)) {
                return;
            }

            Path file = folderPath.resolve(filePath);
            try (Connection connection = DriverManager.getConnection(url);
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(sql);
                    BufferedWriter writer = Files.newBufferedWriter(file)) {

                if (rs.next()) {
                    writer.write(rs.getInt("ID") + ","
                            + rs.getString("Name") + ","
                            + rs.getString("Description") + ","
                            + rs.getString("Completion_Status") + ","
                            + rs.getString("Category"));
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "Task saved to file successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Task not found.");
                }
            }
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Export all tasks to a text file
    public void exportTasksToTextFile(String filePath) {
        String sql = "SELECT * FROM TasksTbl";

        try {
            if (!Files.exists(folderPath)) {
                return;
            }

            Path file = folderPath.resolve(filePath);
            try (Connection connection = DriverManager.getConnection(url);
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(sql);
                    BufferedWriter writer = Files.newBufferedWriter(file)) {

                while (rs.next()) {
                    writer.write(rs.getInt("ID") + ","
                            + rs.getString("Name") + ","
                            + rs.getString("Description") + ","
                            + rs.getString("Completion_Status") + ","
                            + rs.getString("Category"));
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "Tasks saved to file successfully.");
                displayFileProperties(filePath);
            }
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Export all tasks to a CSV file
    public void exportTasksToCSV(String filePath) {
        try {
            if (!Files.exists(folderPath)) {
                return;
            }

            Path file = folderPath.resolve(filePath);
            try (Connection connection = DriverManager.getConnection(url);
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT * FROM TasksTbl");
                    BufferedWriter writer = Files.newBufferedWriter(file)) {

                writer.write("ID,Name,Description,Completion_Status,Category");
                writer.newLine();

                while (rs.next()) {
                    writer.write(rs.getInt("ID") + ","
                            + rs.getString("Name") + ","
                            + rs.getString("Description") + ","
                            + rs.getString("Completion_Status") + ","
                            + rs.getString("Category"));
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "Tasks exported to CSV successfully.");
                displayFileProperties(filePath);
            }
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Read tasks from a text file
    public void readTasksFromTextFile(String filePath) {
        taskList.clear();
        Path file = folderPath.resolve(filePath);
        try (BufferedReader reader = Files.newBufferedReader(file)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(",");
                if (taskData.length == 5) {
                    Tasks task = new Tasks(
                            Integer.parseInt(taskData[0]),
                            taskData[1],
                            taskData[2],
                            taskData[3],
                            taskData[4]
                    );
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File reading error: " + e.getMessage());
        }
    }

    // Read tasks from a CSV file
    public void readTasksFromCSVFile(String filePath) {
        taskList.clear();
        Path file = folderPath.resolve(filePath);
        try (BufferedReader reader = Files.newBufferedReader(file)) {

            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(",");
                if (taskData.length == 5) {
                    Tasks task = new Tasks(
                            Integer.parseInt(taskData[0]),
                            taskData[1],
                            taskData[2],
                            taskData[3],
                            taskData[4]
                    );
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "CSV reading error: " + e.getMessage());
        }
    }

    // Get all file names in the folder
    public List<String> getAllFileNames() {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    fileNames.add(filePath.getFileName().toString());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading folder: " + e.getMessage());
        }
        return fileNames;
    }

    // Populate JComboBox with file names/ add file names to JComboBox 
    public void populateFileComboBox(JComboBox<String> files) {
        if (Files.exists(folderPath)) {
            files.removeAllItems();
            List<String> fileNames = getAllFileNames();
            for (String fileName : fileNames) {
                files.addItem(fileName);
            }
        }

    }

    // Show a scrollable dialog with content
    public void showScrollableDialog(String content) {
        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Task Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // Display task details from a file
    public void displayTaskDetails(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            if (fileName.endsWith(".txt")) {
                readTasksFromTextFile(fileName);
            } else if (fileName.endsWith(".csv")) {
                readTasksFromCSVFile(fileName);
            } else {
                JOptionPane.showMessageDialog(null, "Unsupported file type.");
                return;
            }

            StringBuilder taskDetails = new StringBuilder();
            for (Tasks task : taskList) {
                taskDetails.append("ID: ").append(task.getId()).append("\n")
                        .append("Name: ").append(task.getName()).append("\n")
                        .append("Description: ").append(task.getDescription()).append("\n")
                        .append("Completion Status: ").append(task.getCompletionStatus()).append("\n")
                        .append("Category: ").append(task.getCategory()).append("\n\n");
            }

            showScrollableDialog(taskDetails.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.");
        }
    }
}
