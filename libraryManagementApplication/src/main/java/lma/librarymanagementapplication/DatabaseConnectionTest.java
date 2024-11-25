package lma.librarymanagementapplication;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {
    @Test
    public void testDatabaseConnection() throws DatabaseConnectionException {
        try {
            Connection connection = Database.connectDB();
            assertNotNull(connection, "Không thể kết nối đến cơ sỏ dữ liệu");
            assertFalse(connection.isClosed(), "Không thể mở cơ sở dữ liệu");
            connection.close();
        } catch (Exception e) {
            throw new DatabaseConnectionException("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
