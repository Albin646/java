package jb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Delete {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/schools";
    private static final String USER = "root";
    private static final String PASSWORD = "Albin@19122003";

    // JDBC variables for opening and managing connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to perform actions before the delete operation
    private static void beforeOperation(int id) {
        System.out.println("Preparing to delete record with ID: " + id);
        // Display the table contents before deletion
        System.out.println("Table contents before deletion:");
        displayTableContents();
    }

    // Method to perform actions after the delete operation
    private static void afterOperation(int id, boolean wasDeleted) {
        if (wasDeleted) {
            System.out.println("Record with ID " + id + " deleted successfully.");
        } else {
            System.out.println("No record found with ID " + id + ".");
        }
        // Display the table contents after deletion
        System.out.println("Table contents after deletion:");
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
        // SQL query to delete a record based on a specific condition (e.g., id)
        String deleteSQL = "DELETE FROM students WHERE id = ?";

        // Specify the ID of the record to delete
        int idToDelete = 13;

        // Perform actions before the delete operation
        beforeOperation(idToDelete);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            // Set the value of the parameter (id of the record to be deleted)
            preparedStatement.setInt(1, idToDelete);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Determine if the deletion was successful
            boolean wasDeleted = (rowsAffected > 0);

            // Perform actions after the delete operation
            afterOperation(idToDelete, wasDeleted);

        } catch (SQLException e) {
            // Print SQL exception details to help with debugging
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
