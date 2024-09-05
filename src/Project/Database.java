package Project;

import java.sql.*;
import java.io.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Database {

    public final String url = "jdbc:sqlite:tasksDB";

    public void createDB() {
        String[] values = new String[]{"CREATE TABLE TasksTbl"
            + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "Name VARCHAR (25),"
            + "Description VARCHAR (100),"
            + "Completion_Status VARCHAR (15),"
            + "Category VARCHAR (30)); ", "CREATE TABLE CategoryTbl"
            + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "Category VARCHAR (30)); ", "INSERT INTO CategoryTbl (Category)"
            + "VALUES ('Work');", "INSERT INTO CategoryTbl (Category)"
            + "VALUES ('Personal');", "INSERT INTO CategoryTbl (Category)"
            + "VALUES ('Shopping');", "INSERT INTO CategoryTbl (Category)"
            + "VALUES ('Health & Fitness');", "INSERT INTO CategoryTbl (Category)"
            + "VALUES ('Study');", "INSERT INTO CategoryTbl (Category)"
            + "VALUES ('Home');", "INSERT INTO CategoryTbl (Category)"
            + "VALUES ('Finance');"};

        File f = new File("tasksDB");

        if (!f.exists()) {
            try {
                Connection connection = DriverManager.getConnection(url);

                if (connection != null) {
                    DatabaseMetaData meta = connection.getMetaData();
                    JOptionPane.showMessageDialog(null, "Database succesfully created");

                    boolean res;
                    boolean success = true;
                    Statement statement = connection.createStatement();
                    for (String s : values) {

                        res = statement.execute(s);
                        if (res) {
                            success = false;
                        }
                    }

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Table succesfully created");
                    } else {
                        JOptionPane.showMessageDialog(null, "Table could not be created");
                    }

                    statement.close();
                    connection.close();
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void loadAll(JTable table) {
        File f = new File("tasksDB");

        if (f.exists()) {
            try {
                Connection connection = DriverManager.getConnection(url);

                String sql = "SELECT * FROM TasksTbl";

                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery(sql);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("ID"), rs.getString("Name"), rs.getString("Description"), rs.getString("Completion_Status"), rs.getString("Category")});
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void loadCategory(JComboBox<String> category) {
        File f = new File("tasksDB");

        if (f.exists()) {
            try {
                Connection connection = DriverManager.getConnection(url);

                String sql = "SELECT Category FROM CategoryTbl";

                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    category.addItem(rs.getString("Category"));
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void searchTasks(JTable table, String searchTerm, String selectedColumn) {
        File f = new File("tasksDB");

        if (f.exists()) {
            try {
                Connection connection = DriverManager.getConnection(url);

                String sql;
                if (selectedColumn.isEmpty()) {
                    sql = "SELECT * FROM TasksTbl WHERE "
                            + "ID LIKE '%" + searchTerm + "%' OR "
                            + "Name LIKE '%" + searchTerm + "%' OR "
                            + "Description LIKE '%" + searchTerm + "%' OR "
                            + "Completion_Status LIKE '%" + searchTerm + "%' OR "
                            + "Category LIKE '%" + searchTerm + "%'";
                } else {
                    sql = "SELECT * FROM TasksTbl WHERE " + selectedColumn + " LIKE '%" + searchTerm + "%'";
                }

                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("ID"), rs.getString("Name"), rs.getString("Description"), rs.getString("Completion_Status"), rs.getString("Category")});
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void loadSorted(JTable table, String sortOrder) {
        File f = new File("tasksDB");

        if (f.exists()) {
            try {
                Connection connection = DriverManager.getConnection(url);

                String orderBy = sortOrder.equals("Ascending") ? "ASC" : "DESC";

                String sql = "SELECT * FROM TasksTbl ORDER BY ID " + orderBy;

                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("ID"), rs.getString("Name"), rs.getString("Description"), rs.getString("Completion_Status"), rs.getString("Category")});
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void loadID(JComboBox<String> taskID) {
        File f = new File("tasksDB");

        if (f.exists()) {
            try {
                Connection connection = DriverManager.getConnection(url);

                String sql = "SELECT ID FROM TasksTbl";

                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    taskID.addItem(rs.getString("ID"));
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void removeTask(JComboBox<String> taskID) {
        File f = new File("tasksDB");

        if (f.exists()) {
            try {
                Connection connection = DriverManager.getConnection(url);

                String selectedTaskID = (String) taskID.getSelectedItem();

                String sql = "DELETE FROM TasksTbl WHERE id = '" + selectedTaskID + "'";

                Statement statement = connection.createStatement();

                int rowsDeleted = statement.executeUpdate(sql);
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Task removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "No task found with the selected ID.");
                }

                statement.close();
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void saveTaskToFile(String taskId, String filePath) {
        String sql = "SELECT * FROM TasksTbl WHERE ID = " + taskId;

        try (
                Connection connection = DriverManager.getConnection(url); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql); BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
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
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void exportTasksToTextFile(String filePath) {
        String sql = "SELECT * FROM TasksTbl";

        try (
                Connection connection = DriverManager.getConnection(url); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql); FileOutputStream fileOS = new FileOutputStream(filePath); OutputStreamWriter osWriter = new OutputStreamWriter(fileOS); BufferedWriter writer = new BufferedWriter(osWriter)) {
            while (rs.next()) {
                writer.write(rs.getInt("ID") + ","
                        + rs.getString("Name") + ","
                        + rs.getString("Description") + ","
                        + rs.getString("Completion_Status") + ","
                        + rs.getString("Category"));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Task saved to file successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File error: " + e.getMessage());
        }
    }

    public void exportTasksToCSV(String filePath) {
        try (Connection connection = DriverManager.getConnection(url); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM TasksTbl"); BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {

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
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    public void readTasksFromTextFile(String filePath) {
        try (FileInputStream fileIS = new FileInputStream(filePath); InputStreamReader isReader = new InputStreamReader(fileIS); BufferedReader bfReader = new BufferedReader(isReader)) {

            String line;
            while ((line = bfReader.readLine()) != null) {
                System.out.println(line); // or process the line as needed
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    
    public void displayFileProperties(String filePath) {
        Path path = Paths.get(filePath);
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

}
