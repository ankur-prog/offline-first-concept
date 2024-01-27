package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSynchronizationBlogPost {

    public static void main(String[] args) {
        String sourceDbUrl = "jdbc:postgresql://localhost:5432/blogging-services";
        String targetDbUrl = "jdbc:postgresql://localhost:5431/blogging-services";
        String username = "postgres";
        String password = "admin";

        synchronizeDatabases(sourceDbUrl, targetDbUrl, username, password);
    }

    public static void synchronizeDatabases(String sourceDbUrl, String targetDbUrl, String username, String password) {
        try (Connection sourceConn = DriverManager.getConnection(sourceDbUrl, username, password);
             Connection targetConn = DriverManager.getConnection(targetDbUrl, username, password)) {

            // Synchronize data from source to target
            synchronizeData(sourceConn, targetConn);

            // Synchronize data from target to source
            synchronizeData(targetConn, sourceConn);

            System.out.println("Database synchronization completed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void synchronizeData(Connection sourceConn, Connection targetConn) throws SQLException {
        String selectQuery = "SELECT id, name, email FROM t_author";
        String insertOrUpdateQuery = "INSERT INTO t_author (id, name, email) VALUES (?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, email = EXCLUDED.email";

        try (PreparedStatement selectStmt = sourceConn.prepareStatement(selectQuery);
             ResultSet resultSet = selectStmt.executeQuery();
             PreparedStatement insertOrUpdateStmt = targetConn.prepareStatement(insertOrUpdateQuery)) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                // Set parameters for insertOrUpdateStmt
                insertOrUpdateStmt.setLong(1, id);
                insertOrUpdateStmt.setString(2, name);
                insertOrUpdateStmt.setString(3, email);

                // Execute the insertOrUpdate query
                insertOrUpdateStmt.executeUpdate();
            }
        }
    }
}
