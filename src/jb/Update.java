package jb;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/schools";
    private static final String USER = "root";
    private static final String PASSWORD = "Albin@19122003";

    // JDBC variables for opening and managing connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to perform actions before the update operation
    private static void beforeOperation(int id) {
        System.out.println("Preparing to update record with ID: " + id);
        // Display the table contents before updating
        System.out.println("Table contents before update:");
        displayTableContents();
    }

    // Method to perform actions after the update operation
    private static void afterOperation(int id, boolean wasUpdated) {
        if (wasUpdated) {
            System.out.println("Record with ID " + id + " updated successfully.");
        } else {
            System.out.println("No record found with ID " + id + ".");
        }
        // Display the table contents after updating
        System.out.println("Table contents after update:");
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
        // SQL query to update a record based on a specific condition (e.g., id)
        String updateSQL = "UPDATE students SET name = ?, age = ?, place = ? WHERE id = ?";

        // Specify the ID of the record to update
        int idToUpdate = 14;
        String newName = "vaishnav";
        int newAge = 25;
        String newPlace = "calicut";

        // Perform actions before the update operation
        beforeOperation(idToUpdate);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            // Set the values of the parameters
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, newAge);
            preparedStatement.setString(3, newPlace);
            preparedStatement.setInt(4, idToUpdate);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Determine if the update was successful
            boolean wasUpdated = (rowsAffected > 0);

            // Perform actions after the update operation
            afterOperation(idToUpdate, wasUpdated);

        } catch (SQLException e) {
            // Print SQL exception details to help with debugging
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

