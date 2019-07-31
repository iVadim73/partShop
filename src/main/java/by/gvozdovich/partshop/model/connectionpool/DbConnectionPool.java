package by.gvozdovich.partshop.model.connectionpool;

import by.gvozdovich.partshop.model.exception.ConnectionPoolException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

public class DbConnectionPool { // TODO: 2019-07-09 утечка соеденинений
    private ReentrantLock lock = new ReentrantLock();
    private static List<ProxyConnection> connectionPool;
    private static List<ProxyConnection> usedConnections = new ArrayList<>(); //blockingQueue???
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
    
    public static DbConnectionPool getInstance() throws ConnectionPoolException {
        if (instance != null) {
            return instance;
        }
        List<ProxyConnection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                pool.add(createConnection(URL, USER, PASSWORD));
            } catch (SQLException e) {
                throw new ConnectionPoolException("Create connection fail", e);
            }
        }
        instance = new DbConnectionPool(pool);
        return instance;
    }

    private DbConnectionPool(List<ProxyConnection> pool) {
        this.connectionPool = pool;
    }

    public Connection getConnection() throws ConnectionPoolException {
        lock.lock();
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                try {
                    connectionPool.add(createConnection(URL, USER, PASSWORD));
                } catch (SQLException e) {
                    throw new ConnectionPoolException("Create connection fail", e);
                }
            } else {
                throw new ConnectionPoolException("Maximum pool size reached, no available connections!");
            }
        }
        ProxyConnection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        lock.unlock();
        return connection;
    }

    public boolean returnConnection(Connection connection) {
        if(connection != null) {
            usedConnections.remove(connection);
            return connectionPool.add((ProxyConnection) connection);
        }
        return false;
    }

    private static ProxyConnection createConnection(String url, String user, String password) throws SQLException {
        ProxyConnection connection = new ProxyConnection(DriverManager.getConnection(url, user, password));
        return connection;
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

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
