package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.ConnectionPoolException;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataRepository {
    void addDBEntity(DbEntity dbEntity) throws RepositoryException;
    void updateDBEntity(DbEntity DbEntity) throws RepositoryException;
    void removeDBEntity(DbEntity dbEntity) throws RepositoryException;
    default ResultSet query(DbEntitySpecification specification) throws RepositoryException {
        ResultSet resultSet;
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = specification.specified(connection);
            resultSet = statement.executeQuery();
        } catch (SpecificationException e) {
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
            throw new RepositoryException("Repository execute fail", e);
        }
        getConnectionPool().returnConnection(connection);
        return resultSet;
    }

    default Connection getConnection() throws RepositoryException {
        try {
            Connection connection = getConnectionPool().getConnection();
            return connection;
        } catch (ConnectionPoolException e) {
            throw new RepositoryException("get connection", e);
        }
    }

    default DbConnectionPool getConnectionPool(){
        Logger logger = LogManager.getLogger();
        try {
            return DbConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.fatal("get logger error", e);
            throw new RuntimeException(e);
        }
    }
}
