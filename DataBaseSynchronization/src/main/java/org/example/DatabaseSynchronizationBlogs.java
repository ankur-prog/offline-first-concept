package org.example;
import java.sql.*;

public class DatabaseSynchronizationBlogs {

    public static void main(String[] args) {
        String sourceDbUrl = "jdbc:postgresql://localhost:5431/blogging-services";
        String targetDbUrl = "jdbc:postgresql://20.115.82.54:5432/blogging-services";
        String username = "postgres";
        String password = "admin";

        synchronizeDatabases(sourceDbUrl, targetDbUrl, username, password);
    }

    public static void synchronizeDatabases(String sourceDbUrl, String targetDbUrl, String username, String password) {
        try (Connection sourceConn = DriverManager.getConnection(sourceDbUrl, username, password);
             Connection targetConn = DriverManager.getConnection(targetDbUrl, username, password)) {

            // Synchronize data from source to target
            synchronizeDataFromSourceToTarget(sourceConn, targetConn);

            // Synchronize data from target to source
            synchronizeDataFromTargetToSource(targetConn, sourceConn);

            System.out.println("Database synchronization complete.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void synchronizeDataFromSourceToTarget(Connection sourceConn, Connection targetConn) throws SQLException {
        synchronizeData(sourceConn, targetConn);
    }

    private static void synchronizeDataFromTargetToSource(Connection sourceConn, Connection targetConn) throws SQLException {
        synchronizeData(sourceConn, targetConn);
    }

    private static void synchronizeData(Connection sourceConn, Connection targetConn) throws SQLException {
        String selectQuery = "SELECT id, content, created_at, modified_at, title, t_author_id, version FROM t_blog_post";
        String insertOrUpdateQuery = "INSERT INTO t_blog_post (id, content, created_at, modified_at, title, t_author_id, version) VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET content = EXCLUDED.content, created_at = EXCLUDED.created_at, modified_at = EXCLUDED.modified_at, t_author_id = EXCLUDED.t_author_id, version = EXCLUDED.version";

        try (PreparedStatement selectStmt = sourceConn.prepareStatement(selectQuery);
             ResultSet resultSet = selectStmt.executeQuery();
             PreparedStatement insertOrUpdateStmt = targetConn.prepareStatement(insertOrUpdateQuery)) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String content = resultSet.getString("content");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Timestamp modifiedAt = resultSet.getTimestamp("modified_at");
                String title = resultSet.getString("title");
                long authorId = resultSet.getLong("t_author_id");
                double version = resultSet.getDouble("version");

                // Set parameters for insertOrUpdateStmt
                insertOrUpdateStmt.setLong(1, id);
                insertOrUpdateStmt.setString(2, content);
                insertOrUpdateStmt.setTimestamp(3, createdAt);
                insertOrUpdateStmt.setTimestamp(4, modifiedAt);
                insertOrUpdateStmt.setString(5, title);
                insertOrUpdateStmt.setLong(6, authorId);
                insertOrUpdateStmt.setDouble(7, version);

                // Execute the insertOrUpdate query
                insertOrUpdateStmt.executeUpdate();
            }
        }

        String selectDeletedRecordsQuery = "SELECT id, table_name FROM deleted_records";
        String deleteQueryTemplate = "DELETE FROM %s WHERE id = ?";
        String deleteFromDeletedRecordsQuery = "DELETE FROM deleted_records WHERE id = ? AND table_name = ?";


        try (PreparedStatement selectDeletedRecordsStmt = sourceConn.prepareStatement(selectDeletedRecordsQuery);
             ResultSet deletedRecordsResultSet = selectDeletedRecordsStmt.executeQuery();
             PreparedStatement deleteFromDeletedRecordsStmt = sourceConn.prepareStatement(deleteFromDeletedRecordsQuery)) {

            while (deletedRecordsResultSet.next()) {
                long id = deletedRecordsResultSet.getLong("id");
                String tableName = deletedRecordsResultSet.getString("table_name");
                String deleteQuery = String.format(deleteQueryTemplate, tableName);

                try (PreparedStatement deleteStmt = targetConn.prepareStatement(deleteQuery)) {
                    deleteStmt.setLong(1, id);
                    deleteStmt.executeUpdate();
                }

                deleteFromDeletedRecordsStmt.setLong(1, id);
                deleteFromDeletedRecordsStmt.setString(2, tableName);
                deleteFromDeletedRecordsStmt.executeUpdate();
            }
    }
}
}