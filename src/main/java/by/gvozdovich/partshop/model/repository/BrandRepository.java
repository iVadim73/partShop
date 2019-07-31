package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BrandRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static BrandRepository instance;
    private static final String ADD_SQL = "INSERT INTO brand (name, country, info, is_active) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE brand SET name=(?), country=(?), info=(?), is_active=(?) WHERE brand_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM brand WHERE brand_id=(?)";

    public static BrandRepository getInstance() {
        if(instance == null) {
            instance = new BrandRepository();
        }
        return instance;
    }

    private BrandRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ADD_SQL);
            statement.setString(1, ((Brand) dbEntity).getName());
            statement.setString(2, ((Brand) dbEntity).getCountry());
            statement.setString(3, ((Brand) dbEntity).getInfo());
            statement.setBoolean(4, ((Brand) dbEntity).getIsActive());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add brand", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, ((Brand) dbEntity).getName());
            statement.setString(2, ((Brand) dbEntity).getCountry());
            statement.setString(3, ((Brand) dbEntity).getInfo());
            statement.setBoolean(4, ((Brand) dbEntity).getIsActive());
            statement.setInt(5, ((Brand) dbEntity).getBrandId());
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
            statement.setInt(1, ((Brand) dbEntity).getBrandId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        }
        connectionPool.returnConnection(connection);
    }
}
