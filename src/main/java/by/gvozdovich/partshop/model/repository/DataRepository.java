package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.ConnectionPoolException;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.util.List;

/**
 * encapsulates common functionality interaction with database
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public interface DataRepository {
    int addDBEntity(DbEntity dbEntity) throws RepositoryException;
    void updateDBEntity(DbEntity DbEntity) throws RepositoryException;
    void removeDBEntity(DbEntity dbEntity) throws RepositoryException;
    List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException;

    default Connection getConnection() throws RepositoryException {
        Logger logger = LogManager.getLogger();
        try {
            DbConnectionPool dbConnectionPool = DbConnectionPool.getInstance();
            try {
                return dbConnectionPool.getConnection();
            } catch (ConnectionPoolException e) {
                throw new RepositoryException("get connection", e);
            }
        } catch (ConnectionPoolException e) {
            logger.fatal("get connection pool error", e);
            throw new RuntimeException(e);
        }
    }
}
