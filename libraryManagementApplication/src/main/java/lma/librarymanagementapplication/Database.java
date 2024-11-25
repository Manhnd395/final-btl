package lma.librarymanagementapplication;

import java.sql.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class Database {

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/connections");
        config.setUsername("root");
        config.setPassword("030205");
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection connectDB() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("HikariDataSource is not initialized.");
        }
        return dataSource.getConnection();
    }

}
