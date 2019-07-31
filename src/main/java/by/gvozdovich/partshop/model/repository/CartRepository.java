package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static CartRepository instance;
    private static final String ADD_SQL = "INSERT INTO cart (user_id, part_id, count) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE cart SET user_id=(?), part_id=(?), count=(?) WHERE cart_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM cart WHERE cart_id=(?)";

    public static CartRepository getInstance() {
        if(instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    private CartRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ADD_SQL);
            statement.setInt(1, ((Cart) dbEntity).getUser().getUserId());
            statement.setInt(2, ((Cart) dbEntity).getPart().getPartId());
            statement.setInt(3, ((Cart) dbEntity).getCount());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add cart", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setInt(1, ((Cart) dbEntity).getUser().getUserId());
            statement.setInt(2, ((Cart) dbEntity).getPart().getPartId());
            statement.setInt(3, ((Cart) dbEntity).getCount());
            statement.setInt(4, ((Cart) dbEntity).getCartId());
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
            statement.setInt(1, ((Cart) dbEntity).getCartId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        }
        connectionPool.returnConnection(connection);
    }
}
