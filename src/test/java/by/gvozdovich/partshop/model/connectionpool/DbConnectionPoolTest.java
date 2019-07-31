package by.gvozdovich.partshop.model.connectionpool;

import by.gvozdovich.partshop.model.exception.ConnectionPoolException;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.testng.Assert.*;

public class DbConnectionPoolTest {
    @Test
    public void whenCalledGetConnection_thenCorrect() throws SQLException, ConnectionPoolException {
        DbConnectionPool connectionPool = DbConnectionPool.getInstance();
        assertTrue(connectionPool.getConnection().isValid(1));
    }
}