package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class JdbcCourseProgressRepository implements CourseProgressRepository {
    private static final String TABLE_CREATION_SQL = "CREATE TABLE IF NOT EXISTS course_progress (" +
            "course_name VARCHAR(255) PRIMARY KEY," +
            "progress INTEGER NOT NULL" +
            ")";
    private final String jdbcUrl;

    public JdbcCourseProgressRepository(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        initialize();
    }

    private void initialize() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(TABLE_CREATION_SQL);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize database", e);
        }
    }

    @Override
    public void enrollCourse(String courseName) {
        String sql = "INSERT INTO course_progress(course_name, progress) VALUES(?, 0)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
            statement.executeUpdate();
        } catch (SQLException e) {
            if (!isDuplicateEntryError(e)) {
                throw new IllegalStateException("Failed to enroll course " + courseName, e);
            }
        }
    }

    @Override
    public void updateProgress(String courseName, int progress) {
        String sql = "UPDATE course_progress SET progress = ? WHERE course_name = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, progress);
            statement.setString(2, courseName);
            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Course " + courseName + " is not enrolled");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update progress for " + courseName, e);
        }
    }

    @Override
    public boolean isEnrolled(String courseName) {
        String sql = "SELECT 1 FROM course_progress WHERE course_name = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to check enrollment for " + courseName, e);
        }
    }

    @Override
    public int getProgress(String courseName) {
        String sql = "SELECT progress FROM course_progress WHERE course_name = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("progress");
                }
                throw new IllegalStateException("Course " + courseName + " is not enrolled");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to read progress for " + courseName, e);
        }
    }

    @Override
    public Map<String, Integer> findAllProgress() {
        String sql = "SELECT course_name, progress FROM course_progress";
        Map<String, Integer> progress = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                progress.put(resultSet.getString("course_name"), resultSet.getInt("progress"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load progress", e);
        }
        return progress;
    }

    @Override
    public void replaceAll(Map<String, Integer> progressSnapshot) {
        String deleteSql = "DELETE FROM course_progress";
        String insertSql = "INSERT INTO course_progress(course_name, progress) VALUES(?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            connection.setAutoCommit(false);
            try {
                try (Statement deleteStatement = connection.createStatement()) {
                    deleteStatement.executeUpdate(deleteSql);
                }
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                    for (Map.Entry<String, Integer> entry : progressSnapshot.entrySet()) {
                        insertStatement.setString(1, entry.getKey());
                        insertStatement.setInt(2, entry.getValue());
                        insertStatement.addBatch();
                    }
                    insertStatement.executeBatch();
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to replace progress data", e);
        }
    }

    private boolean isDuplicateEntryError(SQLException exception) {
        String sqlState = exception.getSQLState();
        return "23000".equals(sqlState) || sqlState == null && exception.getMessage() != null &&
                exception.getMessage().toLowerCase().contains("unique");
    }
}
