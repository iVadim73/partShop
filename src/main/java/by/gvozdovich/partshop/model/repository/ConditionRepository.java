package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Condition;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * class that interacts with the database and accumulates in itself all methods to add/update/remove or query the
 * {@link Condition} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ConditionRepository implements DataRepository {
    private static Logger logger = LogManager.getLogger();
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

    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, ((Condition) dbEntity).getName());
            statement.setString(2, ((Condition) dbEntity).getInfo());
            statement.execute();
            logger.debug("condition added :" + dbEntity);

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int autoId = rs.getInt(1);
            return autoId;
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("add condition", e);
        }
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)
        ) {
            statement.setString(1, ((Condition) dbEntity).getName());
            statement.setString(2, ((Condition) dbEntity).getInfo());
            statement.setInt(3, ((Condition) dbEntity).getConditionId());
            statement.execute();
            logger.debug("condition updated :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("update condition", e);
        }
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(REMOVE_SQL)
        ) {
            statement.setInt(1, ((Condition) dbEntity).getConditionId());
            statement.execute();
            logger.debug("condition removed :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("remove condition", e);
        }
    }

    @Override
    public List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException {
        List<DbEntity> conditionList = new ArrayList<>();
        try (
            Connection connection = getConnection();
            PreparedStatement statement = specification.specified(connection);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Condition condition = new Condition.Builder()
                        .withConditionId(resultSet.getInt(ServiceConstant.CONDITION_ID))
                        .withName(resultSet.getString(ServiceConstant.NAME))
                        .withInfo(resultSet.getString(ServiceConstant.INFO))
                        .build();

                conditionList.add(condition);
            }
        } catch (SpecificationException e) {
            logger.error("SpecificationException :" + e);
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("Repository execute fail", e);
        }
        logger.debug("condition query :" + conditionList);
        return conditionList;
    }
}
