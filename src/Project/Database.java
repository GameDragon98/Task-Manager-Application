package Project;

import java.sql.*;
import java.io.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Database {

    public final String url = "jdbc:sqlite:tasksDB";

    // Sets up the database and creates tables if they don't already exist
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

    // Loads all tasks from the database into the given table
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

    // Loads categories from the database into the given combo box
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

    // Searches for tasks based on the search term and column
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

    // Loads tasks into the table, sorted by ID in ascending or descending order
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

    // Loads all task IDs into the combo box
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

    // Removes a task based on the selected ID from the combo box
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
}
