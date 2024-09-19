package jb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Create {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/schools";
    private static final String USER = "root";
    private static final String PASSWORD = "Albin@19122003";

    // JDBC variables for opening and managing connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to perform actions before the create operation
    private static void beforeOperation() {
        System.out.println("Preparing to create a new record.");
        // Display the table contents before creating the new record
        System.out.println("Table contents before creation:");
        displayTableContents();
    }

    // Method to perform actions after the create operation
    private static void afterOperation(boolean wasCreated) {
        if (wasCreated) {
            System.out.println("New record created successfully.");
        } else {
            System.out.println("Failed to create the new record.");
        }
        // Display the table contents after creating the new record
        System.out.println("Table contents after creation:");
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
        // SQL query to insert a new record
        String insertSQL = "INSERT INTO students (name, age, place) VALUES (?, ?, ?)";

        // Specify the details of the new record
        String newName = "albinn pk";
        int newAge = 20;
        String newPlace = "malayamma";

        // Perform actions before the create operation
        beforeOperation();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Set the values of the parameters
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, newAge);
            preparedStatement.setString(3, newPlace);

            // Execute the insert
            int rowsAffected = preparedStatement.executeUpdate();

            // Determine if the creation was successful
            boolean wasCreated = (rowsAffected > 0);

            // Perform actions after the create operation
            afterOperation(wasCreated);

        } catch (SQLException e) {
            // Print SQL exception details to help with debugging
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
