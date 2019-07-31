package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.Condition;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConditionRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static ConditionRepository instance;
    private static final String ADD_SQL = "INSERT INTO conditions (name, info) VALUES (?, ?)";
    private static final String UPDATE_SQL = "UPDATE conditions SET name=(?), info=(?) WHERE condition_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM conditions WHERE condition_id=(?)";

    public static ConditionRepository getInstance() {
        if(instance == null) {
            instance = new ConditionRepository();
        }
        return instance;
    }

    private ConditionRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ADD_SQL);
            statement.setString(1, ((Condition) dbEntity).getName());
            statement.setString(2, ((Condition) dbEntity).getInfo());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add condition", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, ((Condition) dbEntity).getName());
            statement.setString(2, ((Condition) dbEntity).getInfo());
            statement.setInt(3, ((Condition) dbEntity).getConditionId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("update condition", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(REMOVE_SQL);
            statement.setInt(1, ((Condition) dbEntity).getConditionId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove condition", e);
        }
        connectionPool.returnConnection(connection);
    }
}
