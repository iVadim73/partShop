package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.service.BillService;
import by.gvozdovich.partshop.model.service.ConditionService;
import by.gvozdovich.partshop.model.service.PartService;
import by.gvozdovich.partshop.model.service.UserService;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * class that interacts with the database and accumulates in itself all methods to add/update/remove or query the
 * {@link Order} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class OrderRepository implements DataRepository {
    private static OrderRepository instance;
    private static final String ORDER_ADD_SQL = "INSERT INTO orders (part_id, user_id, cost, condition_id, part_count, is_active, bill_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String ORDER_UPDATE_SQL = "UPDATE orders SET part_id=(?), user_id=(?), cost=(?), condition_id=(?), part_count=(?), is_active=(?), bill_id=(?) WHERE order_id=(?)";
    private static final String ORDER_REMOVE_SQL = "DELETE FROM orders WHERE order_id=(?)";
    private static final String USER_UPDATE_SQL = "UPDATE user SET login=(?), password=(?), email=(?), phone=(?), name=(?), discount=(?), star=(?), comment=(?), bill=(?), role_id=(?), is_active=(?) WHERE user_id=(?)";
    private static final String BILL_ADD_SQL = "INSERT INTO bill (user_id, sum, bill_info_id) VALUES (?, ?, ?)";
    private static final String CART_REMOVE_SQL = "DELETE FROM cart WHERE cart_id=(?)";
    private static final int MINUS_BILL_INFO_ID = 1;
    private static final int PROCESSING_CONDITION_ID = 1;
    private static final boolean DEFAULT_IS_ACTIVE = true;

    public static OrderRepository getInstance() {
        if(instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    private OrderRepository() {

    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(ORDER_ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ((Order) dbEntity).getPart().getPartId());
            statement.setInt(2, ((Order) dbEntity).getUser().getUserId());
            statement.setBigDecimal(3, ((Order) dbEntity).getCost());
            statement.setInt(4, ((Order) dbEntity).getCondition().getConditionId());
            statement.setInt(5, ((Order) dbEntity).getPartCount());
            statement.setBoolean(6, ((Order) dbEntity).getIsActive());
            statement.setInt(7, ((Order) dbEntity).getBill().getBillId());
            statement.execute();

            rs = statement.getGeneratedKeys();
            rs.next();
            int autoId = rs.getInt(1);
            return autoId;
        } catch (SQLException e) {
            throw new RepositoryException("add order", e);
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
            statement = connection.prepareStatement(ORDER_UPDATE_SQL);
            statement.setInt(1, ((Order) dbEntity).getPart().getPartId());
            statement.setInt(2, ((Order) dbEntity).getUser().getUserId());
            statement.setBigDecimal(3, ((Order) dbEntity).getCost());
            statement.setInt(4, ((Order) dbEntity).getCondition().getConditionId());
            statement.setInt(5, ((Order) dbEntity).getPartCount());
            statement.setBoolean(6, ((Order) dbEntity).getIsActive());
            statement.setInt(7, ((Order) dbEntity).getBill().getBillId());
            statement.setInt(8, ((Order) dbEntity).getOrderId());
            statement.execute();
        } catch (SQLException e) {
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
            statement = connection.prepareStatement(ORDER_REMOVE_SQL);
            statement.setInt(1, ((Order) dbEntity).getOrderId());
            statement.execute();
        } catch (SQLException e) {
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
        List<DbEntity> orderList = new ArrayList<>();
        try {
            statement = specification.specified(connection);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                int partId = resultSet.getInt(ServiceConstant.PART_ID);
                int conditionId = resultSet.getInt(ServiceConstant.CONDITION_ID);
                int billId = resultSet.getInt(ServiceConstant.BILL_ID);
                User user;
                Part part;
                Condition condition;
                Bill bill;
                try {
                    user = UserService.getInstance().takeUserById(userId);
                    part = PartService.getInstance().takePartById(partId);
                    condition = ConditionService.getInstance().takeConditionById(conditionId);
                    bill = BillService.getInstance().takeBillById(billId);
                } catch (ServiceException e) {
                    throw new RepositoryException("take user or part or condition or bill fail", e);
                }

                LocalDate orderDate = Timestamp.valueOf(resultSet.getString(ServiceConstant.ORDER_DATE))
                        .toLocalDateTime().toLocalDate();
                LocalDate conditionDate = Timestamp.valueOf(resultSet.getString(ServiceConstant.CONDITION_DATE))
                        .toLocalDateTime().toLocalDate();

                Order order = new Order.Builder()
                        .withOrderId(resultSet.getInt(ServiceConstant.ORDER_ID))
                        .withUser(user)
                        .withPart(part)
                        .withDateOrder(orderDate)
                        .withCost(resultSet.getBigDecimal(ServiceConstant.COST))
                        .withCondition(condition)
                        .withDateCondition(conditionDate)
                        .withPartCount(resultSet.getInt(ServiceConstant.PART_COUNT))
                        .withIsActive(resultSet.getBoolean(ServiceConstant.IS_ACTIVE))
                        .withBill(bill)
                        .build();

                orderList.add(order);
            }
        } catch (SpecificationException e) {
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
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
        return orderList;
    }

    public int buy(Cart cart) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        int autoId = 0;
        try {
            connection.setAutoCommit(false);
            Savepoint buySavepoint = connection.setSavepoint("buySavepoint");

            User user = cart.getUser();
            Part part = cart.getPart();
            int cartId = cart.getCartId();
            int partCount = cart.getCount();
            int userId = user.getUserId();
            int partId = part.getPartId();
            Role userType = user.getRole();
            int roleId = userType.getRoleId();
            BigDecimal userBill = user.getBill();
            BigDecimal price = part.getPrice();
            BigDecimal sum = price.multiply(BigDecimal.valueOf(partCount));
            double discount = user.getDiscount();
            double ratio = (100 - discount) / 100;
            BigDecimal costWithDiscount = sum.multiply(BigDecimal.valueOf(ratio));
            BigDecimal newUserBill = userBill.subtract(costWithDiscount);

            try {
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
                statement.setInt(10, roleId);
                statement.setBoolean(11, user.getIsActive());
                statement.setInt(12, user.getUserId());
                statement.execute();

                statement = connection.prepareStatement(BILL_ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, userId);
                statement.setBigDecimal(2, costWithDiscount);
                statement.setInt(3, MINUS_BILL_INFO_ID);
                statement.execute();

                rs = statement.getGeneratedKeys();
                rs.next();
                int autoBillId = rs.getInt(1);

                statement = connection.prepareStatement(ORDER_ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, partId);
                statement.setInt(2, userId);
                statement.setBigDecimal(3, costWithDiscount);
                statement.setInt(4, PROCESSING_CONDITION_ID);
                statement.setInt(5, partCount);
                statement.setBoolean(6, DEFAULT_IS_ACTIVE);
                statement.setInt(7, autoBillId);
                statement.execute();

                rs = statement.getGeneratedKeys();
                rs.next();
                autoId = rs.getInt(1);

                statement = connection.prepareStatement(CART_REMOVE_SQL);
                statement.setInt(1, cartId);
                statement.execute();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback(buySavepoint);
                // TODO: 2019-07-29 log
            }
            connection.setAutoCommit(true);
            return autoId;
        } catch (SQLException e) {
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
}
