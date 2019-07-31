package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.entity.Feedback;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static FeedbackRepository instance;
    private static final String ADD_SQL = "INSERT INTO feedback (user_id, part_id, comment, star) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE feedback SET user_id=(?), part_id=(?), comment=(?), star=(?) WHERE feedback_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM feedback WHERE feedback_id=(?)";

    public static FeedbackRepository getInstance() {
        if(instance == null) {
            instance = new FeedbackRepository();
        }
        return instance;
    }

    private FeedbackRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ADD_SQL);
            statement.setInt(1, ((Feedback) dbEntity).getUser().getUserId());
            statement.setInt(2, ((Feedback) dbEntity).getPart().getPartId());
            statement.setString(3, ((Feedback) dbEntity).getComment());
            statement.setInt(4, ((Feedback) dbEntity).getStar());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add feedback", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setInt(1, ((Feedback) dbEntity).getUser().getUserId());
            statement.setInt(2, ((Feedback) dbEntity).getPart().getPartId());
            statement.setString(3, ((Feedback) dbEntity).getComment());
            statement.setInt(4, ((Feedback) dbEntity).getStar());
            statement.setInt(5, ((Feedback) dbEntity).getFeedbackId());
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
            statement = connection.prepareStatement(REMOVE_SQL);
            statement.setInt(1, ((Feedback) dbEntity).getFeedbackId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        }
        connectionPool.returnConnection(connection);
    }
}
