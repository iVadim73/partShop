package by.gvozdovich.partshop.model.connectionpool;

import by.gvozdovich.partshop.model.exception.ConnectionPoolException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

/**
 * pool of connections which can be used to interact with database
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class DbConnectionPool {
    private ReentrantLock lock = new ReentrantLock();
    private static List<ProxyConnection> connectionPool;
    private static List<ProxyConnection> usedConnections = new ArrayList<>();
    private static DbConnectionPool instance;

    private static final int INITIAL_POOL_SIZE;
    private static final int MAX_POOL_SIZE;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        URL = resourceBundle.getString("url");
        USER = resourceBundle.getString("user");
        PASSWORD = resourceBundle.getString("password");
        INITIAL_POOL_SIZE = Integer.valueOf(resourceBundle.getString("initialPoolSize"));
        MAX_POOL_SIZE = Integer.valueOf(resourceBundle.getString("maxPoolSize"));
    }

    /**
     * @return an instance of the connection pool that is configured and ready for issuing and returning connections
     */
    public static DbConnectionPool getInstance() throws ConnectionPoolException {
        if (instance != null) {
            return instance;
        }
        List<ProxyConnection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                pool.add(createConnection());
            } catch (SQLException e) {
                throw new ConnectionPoolException("Create connection fail", e);
            }
        }
        instance = new DbConnectionPool(pool);
        return instance;
    }

    private DbConnectionPool(List<ProxyConnection> pool) {
        connectionPool = pool;
    }

    /**
     * method provides a connection to interact with database
     * @return connection which is ready for statement
     * @throws InterruptedException if waiting of connection was interrupted
     */
    public Connection getConnection() throws ConnectionPoolException {
        lock.lock();
        int size = getSize();
        if (size < INITIAL_POOL_SIZE) {
            for (int i = size; i < INITIAL_POOL_SIZE; i++) {
                try {
                    connectionPool.add(createConnection());
                } catch (SQLException e) {
                    throw new ConnectionPoolException("Create connection fail", e);
                }
            }
        }

        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                try {
                    connectionPool.add(createConnection());
                } catch (SQLException e) {
                    throw new ConnectionPoolException("Create connection fail", e);
                }
            } else {
                throw new ConnectionPoolException("Maximum pool size reached, no available connections!");
            }
        }
        ProxyConnection connection =  connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        lock.unlock();
        return connection;
    }

    /**
     * method returns connection to the pool after sql-execution
     * @param connection connection to be released
     */
    boolean returnConnection(Connection connection) {
        if(connection != null) {
            usedConnections.remove(connection);
            return connectionPool.add((ProxyConnection) connection);
        }
        return false;
    }

    private static ProxyConnection createConnection() throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(URL, USER, PASSWORD));
    }

    /**
     * method returns size of connection pool
     */
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    /**
     * method provides a connection to interact with database
     */
    public boolean shutdown() throws ConnectionPoolException {
        usedConnections.forEach(this::returnConnection);
        for (Connection c : connectionPool) {
            try {
                c.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException("Close connection fail", e);
            }
        }
        connectionPool.clear();
        return connectionPool.isEmpty();
    }
}
