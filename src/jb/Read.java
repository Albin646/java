package jb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Read {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/schools";
    private static final String USER = "root";
    private static final String PASSWORD = "Albin@19122003";

    // JDBC variables for opening and managing connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to perform actions before the read operation
    private static void beforeOperation() {
        System.out.println("Preparing to read records.");
        // Display the table contents before reading
        System.out.println("Table contents:");
        displayTableContents();
    }

    // Method to display the contents of the students table
    private static void displayTableContents() {
        String querySQL = "SELECT * FROM students";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Print table headers
            System.out.printf("%-5s %-20s %-3s %-20s%n", "ID", "Name", "Age", "Place");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String place = resultSet.getString("place");

                // Print table row
                System.out.printf("%-5d %-20s %-3d %-20s%n", id, name, age, place);
            }

        } catch (SQLException e) {
            // Print SQL exception details to help with debugging
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Perform actions before the read operation
        beforeOperation();

        // No specific SQL query to execute in this case as we're displaying all records
    }
}
