package ema.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import io.github.cdimascio.dotenv.*;

/**
 * This class handles the database connections and queries. 
 */
public class DatabaseHandler {
    /**
     * Creates a connection to the database.
     * @return The connection
     */
    public static Connection getConnection() {
        // Get databse credentials
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");

        // Create a connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insert the given data into the scoreboard table.
     * @param name The player's name
     * @param score The player's score
     * @return True if the insertion was succesful and false if not.
     */
    public static boolean insertScore(String name, int score) {
        Connection connection = null;
        PreparedStatement statement = null;

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);

        String query = "INSERT INTO Scoreboard (player_name, player_score, date) VALUES (?, ?, ?)";
        
        try {
            connection = getConnection();

            if(connection != null) {
                connection.setAutoCommit(false);

                statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setInt(2, score);
                statement.setString(3, date);

                int rowsInserted = statement.executeUpdate();

                if(rowsInserted > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback the transaction in case of an error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            try {
                if(statement != null) statement.close();
                if(connection != null) connection.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Connection closed - Insert");
        }
        return false;
    }

    
    public static String[][] getScoreboardData() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String query = "SELECT player_name, player_score, date FROM Scoreboard ORDER BY player_score DESC LIMIT 10";

        try {
            connection = getConnection();

            statement = connection.prepareStatement(query);

            result = statement.executeQuery();

            return formatData(result);
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(result != null) result.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Connection closed - Scoreboard");   
        }
        return new String[10][4];
    }

    public static String[][] formatData(ResultSet resultSet) {
        if(resultSet == null) {
            return null;
        }

        List<String[]> dataList = new ArrayList<>();
        int position = 1;

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("player_name");
                int score = resultSet.getInt("player_score");
                Date date = resultSet.getDate("date");

                String[] row = new String[4];
                row[0] = String.valueOf(position);
                row[1] = name;
                row[2] = String.valueOf(score);
                row[3] = date.toString();

                dataList.add(row);
                position++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Convert List to 2D Array
        String[][] formattedResult = new String[dataList.size()][4];
        for (int i = 0; i < dataList.size(); i++) {
            formattedResult[i] = dataList.get(i);
        }

        return formattedResult;
    }
}
