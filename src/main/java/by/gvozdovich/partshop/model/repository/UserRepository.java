package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;

import java.math.BigDecimal;
import java.sql.*;

public class UserRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static UserRepository instance;
    private static final String USER_ADD_SQL = "INSERT INTO user (login, password, email, phone, name, discount, star, comment, bill, role_id, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String USER_UPDATE_SQL = "UPDATE user SET login=(?), password=(?), email=(?), phone=(?), name=(?), discount=(?), star=(?), comment=(?), bill=(?), role_id=(?), is_active=(?) WHERE user_id=(?)";
    private static final String USER_REMOVE_SQL = "DELETE FROM user WHERE user_id=(?)";

    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(USER_ADD_SQL);
            statement.setString(1, ((User) dbEntity).getLogin());
            statement.setString(2, ((User) dbEntity).getPassword());
            statement.setString(3, ((User) dbEntity).getEmail());
            statement.setLong(4, ((User) dbEntity).getPhone());
            statement.setString(5, ((User) dbEntity).getName());
            statement.setDouble(6, ((User) dbEntity).getDiscount());
            statement.setInt(7, ((User) dbEntity).getStar());
            statement.setString(8, ((User) dbEntity).getComment());
            statement.setBigDecimal(9, ((User) dbEntity).getBill());
            statement.setInt(10, ((User) dbEntity).getRole().getRoleId());
            statement.setBoolean(11,((User) dbEntity).getIsActive());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add user", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(USER_UPDATE_SQL);
            statement.setString(1, ((User) dbEntity).getLogin());
            statement.setString(2, ((User) dbEntity).getPassword());
            statement.setString(3, ((User) dbEntity).getEmail());
            statement.setLong(4, ((User) dbEntity).getPhone());
            statement.setString(5, ((User) dbEntity).getName());
            statement.setDouble(6, ((User) dbEntity).getDiscount());
            statement.setInt(7, ((User) dbEntity).getStar());
            statement.setString(8, ((User) dbEntity).getComment());
            statement.setBigDecimal(9, ((User) dbEntity).getBill());
            statement.setInt(10, ((User) dbEntity).getRole().getRoleId());
            statement.setBoolean(11,((User) dbEntity).getIsActive());
            statement.setInt(12, ((User) dbEntity).getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
        throw new RepositoryException("update", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(USER_REMOVE_SQL);
            statement.setInt(1, ((User) dbEntity).getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        }
        connectionPool.returnConnection(connection);
    }
}
