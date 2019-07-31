package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static PartRepository instance;
    private static final String ADD_SQL = "INSERT INTO part (catalog_no, original_catalog_no, info, price, picture, wait, brand_id, stock_count, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE part SET catalog_no=(?), original_catalog_no=(?), info=(?), price=(?), picture=(?), wait=(?), brand_id=(?), stock_count=(?), is_active=(?) WHERE part_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM part WHERE part_id=(?)";

    public static PartRepository getInstance() {
        if(instance == null) {
            instance = new PartRepository();
        }
        return instance;
    }

    private PartRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ADD_SQL);
            statement.setString(1, ((Part) dbEntity).getCatalogNo());
            statement.setString(2, ((Part) dbEntity).getOriginalCatalogNo());
            statement.setString(3, ((Part) dbEntity).getInfo());
            statement.setBigDecimal(4, ((Part) dbEntity).getPrice());
            statement.setString(5, ((Part) dbEntity).getPictureURL());
            statement.setInt(6, ((Part) dbEntity).getWait());
            statement.setInt(7, ((Part) dbEntity).getBrand().getBrandId());
            statement.setInt(8, ((Part) dbEntity).getStockCount());
            statement.setBoolean(9, ((Part) dbEntity).getIsActive());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add part", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, ((Part) dbEntity).getCatalogNo());
            statement.setString(2, ((Part) dbEntity).getOriginalCatalogNo());
            statement.setString(3, ((Part) dbEntity).getInfo());
            statement.setBigDecimal(4, ((Part) dbEntity).getPrice());
            statement.setString(5, ((Part) dbEntity).getPictureURL());
            statement.setInt(6, ((Part) dbEntity).getWait());
            statement.setInt(7, ((Part) dbEntity).getBrand().getBrandId());
            statement.setInt(8, ((Part) dbEntity).getStockCount());
            statement.setBoolean(9, ((Part) dbEntity).getIsActive());
            statement.setInt(10, ((Part) dbEntity).getPartId());
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
            statement.setInt(1, ((Part) dbEntity).getPartId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        }
        connectionPool.returnConnection(connection);
    }
}
