package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.entity.Role;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static RoleRepository instance;
    private static final String ADD_SQL = "INSERT INTO role (type) VALUES (?)";
    private static final String UPDATE_SQL = "UPDATE role SET type=(?) WHERE role_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM role WHERE role_id=(?)";

    public static RoleRepository getInstance() {
        if(instance == null) {
            instance = new RoleRepository();
        }
        return instance;
    }

    private RoleRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ADD_SQL);
            statement.setString(1, ((Role) dbEntity).getType());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add role", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, ((Role) dbEntity).getType());
            statement.setInt(2, ((Role) dbEntity).getRoleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("update role", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(REMOVE_SQL);
            statement.setInt(1, ((Role) dbEntity).getRoleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove role", e);
        }
        connectionPool.returnConnection(connection);
    }
}
