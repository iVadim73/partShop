package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.BillInfo;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillInfoRepository implements DataRepository {
    private DbConnectionPool connectionPool;
    private static BillInfoRepository instance;
    private static final String ADD_SQL = "INSERT INTO bill_info (info) VALUES (?)";
    private static final String UPDATE_SQL = "UPDATE bill_info SET info=(?) WHERE bill_info_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM bill_info WHERE bill_info_id=(?)";

    public static BillInfoRepository getInstance() {
        if(instance == null) {
            instance = new BillInfoRepository();
        }
        return instance;
    }

    private BillInfoRepository() {
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ADD_SQL);
            statement.setString(1, ((BillInfo) dbEntity).getInfo());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add info", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, ((BillInfo) dbEntity).getInfo());
            statement.setInt(2, ((BillInfo) dbEntity).getBillInfoId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("update bill info", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(REMOVE_SQL);
            statement.setInt(1, ((BillInfo) dbEntity).getBillInfoId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove bill info", e);
        }
        connectionPool.returnConnection(connection);
    }
}
