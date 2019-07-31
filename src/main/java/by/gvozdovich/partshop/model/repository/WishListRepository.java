package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.entity.WishList;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WishListRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static WishListRepository instance;
    private static final String ADD_SQL = "INSERT INTO wish_list (user_id, part_id) VALUES (?, ?)";
    private static final String UPDATE_SQL = "UPDATE wish_list SET user_id=(?), part_id=(?) WHERE wish_list_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM wish_list WHERE wish_list_id=(?)";

    public static WishListRepository getInstance() {
        if(instance == null) {
            instance = new WishListRepository();
        }
        return instance;
    }

    private WishListRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ADD_SQL);
            statement.setInt(1, ((WishList) dbEntity).getUser().getUserId());
            statement.setInt(2, ((WishList) dbEntity).getPart().getPartId());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add wishList", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setInt(1, ((WishList) dbEntity).getUser().getUserId());
            statement.setInt(2, ((WishList) dbEntity).getPart().getPartId());
            statement.setInt(3, ((WishList) dbEntity).getWishListId());
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
            statement.setInt(1, ((WishList) dbEntity).getWishListId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        }
        connectionPool.returnConnection(connection);
    }
}
