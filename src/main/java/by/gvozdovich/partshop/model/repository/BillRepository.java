package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.service.BillInfoService;
import by.gvozdovich.partshop.model.service.UserService;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * class that interacts with the database and accumulates in itself all methods to add/update/remove or query the
 * {@link Bill} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class BillRepository implements DataRepository {
    private static Logger logger = LogManager.getLogger();
    private static BillRepository instance;
    private static final String BILL_ADD_SQL = "INSERT INTO bill (user_id, sum, bill_info_id) VALUES (?, ?, ?)";
    private static final String BILL_UPDATE_SQL = "UPDATE bill SET user_id=(?), sum=(?), bill_info_id=(?) WHERE bill_id=(?)";
    private static final String BILL_REMOVE_SQL = "DELETE FROM bill WHERE bill_id=(?)";
    private static final String USER_UPDATE_SQL = "UPDATE user SET login=(?), password=(?), email=(?), phone=(?), name=(?), discount=(?), star=(?), comment=(?), bill=(?), role_id=(?), is_active=(?) WHERE user_id=(?)";

    public static BillRepository getInstance() {
        if(instance == null) {
            instance = new BillRepository();
        }
        return instance;
    }

    private BillRepository() {
    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        int autoId = 0;

        try {
            connection.setAutoCommit(false);
            Savepoint addBillSavepoint = connection.setSavepoint("addBillSavepoint");

            User user = ((Bill) dbEntity).getUser();
            int userId = user.getUserId();
            BigDecimal sum = ((Bill) dbEntity).getSum();
            BigDecimal userBill = user.getBill();
            BigDecimal newUserBill = userBill.add(sum);

            try {
                statement = connection.prepareStatement(BILL_ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, userId);
                statement.setBigDecimal(2, sum);
                statement.setInt(3, ((Bill) dbEntity).getBillInfo().getBillInfoId());
                statement.execute();


                rs = statement.getGeneratedKeys();
                rs.next();
                autoId = rs.getInt(1);

                statement = connection.prepareStatement(USER_UPDATE_SQL);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setLong(4, user.getPhone());
                statement.setString(5, user.getName());
                statement.setDouble(6, user.getDiscount());
                statement.setInt(7, user.getStar());
                statement.setString(8, user.getComment());
                statement.setBigDecimal(9, newUserBill);
                statement.setInt(10, user.getRole().getRoleId());
                statement.setBoolean(11, user.getIsActive());
                statement.setInt(12, userId);
                statement.execute();

                connection.commit();
                logger.debug("bill added :" + dbEntity);
            } catch (SQLException e) {
                connection.rollback(addBillSavepoint);
                logger.error("SQLException :" + e);
            }

            connection.setAutoCommit(true);

            return autoId;
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("SQL error", e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(BILL_UPDATE_SQL);
            statement.setInt(1, ((Bill) dbEntity).getUser().getUserId());
            statement.setBigDecimal(2, ((Bill) dbEntity).getSum());
            statement.setInt(3, ((Bill) dbEntity).getBillInfo().getBillInfoId());
            statement.setInt(4, ((Bill) dbEntity).getBillId());
            statement.execute();
            logger.debug("bill updated :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("update", e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(BILL_REMOVE_SQL);
            statement.setInt(1, ((Bill) dbEntity).getBillId());
            statement.execute();
            logger.debug("bill removed :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("remove", e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException {
        ResultSet resultSet = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<DbEntity> billList = new ArrayList<>();
        try {
            statement = specification.specified(connection);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                int billInfoId = resultSet.getInt(ServiceConstant.BILL_INFO_ID);
                User user;
                BillInfo billInfo;
                try {
                    user = UserService.getInstance().takeUserById(userId);
                    billInfo = BillInfoService.getInstance().takeBillInfoById(billInfoId);
                } catch (ServiceException e) {
                    throw new RepositoryException("take user or bill info fail", e);
                }
                LocalDate date = Timestamp.valueOf(resultSet.getString(ServiceConstant.DATE))
                        .toLocalDateTime().toLocalDate();

                Bill bill = new Bill.Builder()
                        .withBillId(resultSet.getInt(ServiceConstant.BILL_ID))
                        .withUser(user)
                        .withSum(resultSet.getBigDecimal(ServiceConstant.SUM))
                        .withBillInfo(billInfo)
                        .withDate(date)
                        .build();

                billList.add(bill);
            }
        } catch (SpecificationException e) {
            logger.error("SpecificationException :" + e);
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("Repository execute fail", e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
        logger.debug("bill query :" + billList);
        return billList;
    }
}