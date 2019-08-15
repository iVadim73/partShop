package by.gvozdovich.partshop.model.connectionpool;

import by.gvozdovich.partshop.model.exception.ConnectionPoolException;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.testng.Assert.*;

public class DbConnectionPoolTest {
    @Test
    public void returnConnectionTest() throws SQLException, ConnectionPoolException {

        DbConnectionPool dbConnectionPool = DbConnectionPool.getInstance();
        int startCount = dbConnectionPool.getSize();

        for(int i = 0; i < 1000; i++) {
            Connection connection = dbConnectionPool.getConnection();
            connection.close();
        }

        int currentCount = dbConnectionPool.getSize();

        assertTrue(startCount == currentCount);
    }
}