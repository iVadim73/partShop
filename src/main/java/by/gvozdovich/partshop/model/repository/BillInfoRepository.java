package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.BillInfo;
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
 * {@link BillInfo} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class BillInfoRepository implements DataRepository {
    private static Logger logger = LogManager.getLogger();
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
    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement =  connection.prepareStatement(ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, ((BillInfo) dbEntity).getInfo());
            statement.execute();
            logger.debug("billInfo added :" + dbEntity);

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int autoId = rs.getInt(1);
            return autoId;
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("add bill info fail", e);
        }
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)
        ) {
            statement.setString(1, ((BillInfo) dbEntity).getInfo());
            statement.setInt(2, ((BillInfo) dbEntity).getBillInfoId());
            statement.execute();
            logger.debug("billInfo updated :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("update bill info", e);
        }
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(REMOVE_SQL)
        ) {
            statement.setInt(1, ((BillInfo) dbEntity).getBillInfoId());
            statement.execute();
            logger.debug("billInfo removed :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("remove bill info", e);
        }
    }

    @Override
    public List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException {
        List<DbEntity> billInfoList = new ArrayList<>();
        try (
            Connection connection = getConnection();
            PreparedStatement statement = specification.specified(connection);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                BillInfo billInfo = new BillInfo.Builder()
                        .withBillInfoId(resultSet.getInt(ServiceConstant.BILL_INFO_ID))
                        .withInfo(resultSet.getString(ServiceConstant.INFO))
                        .build();

                billInfoList.add(billInfo);
            }
        } catch (SpecificationException e) {
            logger.error("SpecificationException :" + e);
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("Repository execute fail", e);
        }
        logger.debug("billInfo list query :" + billInfoList);
        return billInfoList;
    }
}
