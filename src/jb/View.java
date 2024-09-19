package jb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/schools";
    private static final String USER = "root";
    private static final String PASSWORD = "Albin@19122003";

    // JDBC variables for opening and managing connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        // SQL query to retrieve data from the students table
        String querySQL = "SELECT * FROM students";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Iterate through the result set and display data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String place = resultSet.getString("place");

                // Print the data to the console
                System.out.printf("ID: %d, Name: %s, Age: %d, Place: %s%n", id, name, age, place);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

