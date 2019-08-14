package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.service.RoleService;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * class that interacts with the database and accumulates in itself all methods to add/update/remove or query the
 * {@link User} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UserRepository implements DataRepository {
    private static Logger logger = LogManager.getLogger();
    private static UserRepository instance;
    private static final String USER_ADD_SQL = "INSERT INTO user (login, password, email, phone, name, discount, star, comment, bill, role_id, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String USER_UPDATE_SQL = "UPDATE user SET login=(?), password=(?), email=(?), phone=(?), name=(?), discount=(?), star=(?), comment=(?), bill=(?), role_id=(?), is_active=(?) WHERE user_id=(?)";
    private static final String USER_REMOVE_SQL = "DELETE FROM user WHERE user_id=(?)";

    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {

    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(USER_ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ((User) dbEntity).getLogin());
            statement.setString(2, ((User) dbEntity).getPassword());
            statement.setString(3, ((User) dbEntity).getEmail());
            statement.setLong(4, ((User) dbEntity).getPhone());
            statement.setString(5, ((User) dbEntity).getName());
            statement.setDouble(6, ((User) dbEntity).getDiscount());
            statement.setInt(7, ((User) dbEntity).getStar());
            statement.setString(8, ((User) dbEntity).getComment());
            statement.setBigDecimal(9, ((User) dbEntity).getBill());
            statement.setInt(10, ((User) dbEntity).getRole().getRoleId());
            statement.setBoolean(11,((User) dbEntity).getIsActive());
            statement.execute();
            logger.debug("user added :" + dbEntity);

            rs = statement.getGeneratedKeys();
            rs.next();
            int autoId = rs.getInt(1);
            return autoId;
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("add user", e);
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
            statement = connection.prepareStatement(USER_UPDATE_SQL);
            statement.setString(1, ((User) dbEntity).getLogin());
            statement.setString(2, ((User) dbEntity).getPassword());
            statement.setString(3, ((User) dbEntity).getEmail());
            statement.setLong(4, ((User) dbEntity).getPhone());
            statement.setString(5, ((User) dbEntity).getName());
            statement.setDouble(6, ((User) dbEntity).getDiscount());
            statement.setInt(7, ((User) dbEntity).getStar());
            statement.setString(8, ((User) dbEntity).getComment());
            statement.setBigDecimal(9, ((User) dbEntity).getBill());
            statement.setInt(10, ((User) dbEntity).getRole().getRoleId());
            statement.setBoolean(11,((User) dbEntity).getIsActive());
            statement.setInt(12, ((User) dbEntity).getUserId());
            statement.execute();
            logger.debug("user updated :" + dbEntity);
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
            statement = connection.prepareStatement(USER_REMOVE_SQL);
            statement.setInt(1, ((User) dbEntity).getUserId());
            statement.execute();
            logger.debug("user removed :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("remove", e);
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
    public List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException {
        ResultSet resultSet = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<DbEntity> userList = new ArrayList<>();
        try {
            statement = specification.specified(connection);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int roleId = resultSet.getInt(ServiceConstant.ROLE_ID);
                Role role;
                try {
                    role = RoleService.getInstance().takeRoleById(roleId);
                } catch (ServiceException e) {
                    logger.error("ServiceException :" + e);
                    throw new RepositoryException("take role fail", e);
                }
                LocalDate registrationDate = Timestamp.valueOf(resultSet.getString(ServiceConstant.REGISTRATION_DATE))
                        .toLocalDateTime().toLocalDate();

                User user = new User.Builder()
                        .withUserId(resultSet.getInt(ServiceConstant.USER_ID))
                        .withLogin(resultSet.getString(ServiceConstant.LOGIN))
                        .withPassword(resultSet.getString(ServiceConstant.PASSWORD))
                        .withEmail(resultSet.getString(ServiceConstant.EMAIL))
                        .withPhone(resultSet.getLong(ServiceConstant.PHONE))
                        .withName(resultSet.getString(ServiceConstant.NAME))
                        .withRegistrationDate(registrationDate)
                        .withDiscount(resultSet.getDouble(ServiceConstant.DISCOUNT))
                        .withStar(resultSet.getInt(ServiceConstant.STAR))
                        .withComment(resultSet.getString(ServiceConstant.COMMENT))
                        .withBill(resultSet.getBigDecimal(ServiceConstant.BILL))
                        .withType(role)
                        .withIsActive(resultSet.getBoolean(ServiceConstant.IS_ACTIVE))
                        .build();

                userList.add(user);
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
        logger.debug("user query :" + userList);
        return userList;
    }
}
