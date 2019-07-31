package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import java.math.BigDecimal;
import java.sql.*;

public class OrderRepository implements DataRepository {
    private DbConnectionPool connectionPool;
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
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(ORDER_ADD_SQL);
            statement.setInt(1, ((Order) dbEntity).getPart().getPartId());
            statement.setInt(2, ((Order) dbEntity).getUser().getUserId());
            statement.setBigDecimal(3, ((Order) dbEntity).getCost());
            statement.setInt(4, ((Order) dbEntity).getCondition().getConditionId());
            statement.setInt(5, ((Order) dbEntity).getPartCount());
            statement.setBoolean(6, ((Order) dbEntity).getIsActive());
            statement.setInt(7, ((Order) dbEntity).getBill().getBillId());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("add order", e);
        }
        connectionPool.returnConnection(connection);
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
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
            statement = connection.prepareStatement(ORDER_REMOVE_SQL);
            statement.setInt(1, ((Order) dbEntity).getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        }
        connectionPool.returnConnection(connection);
    }

    public void buy(DbEntity dbEntity) throws RepositoryException { // TODO: 2019-07-29 норм???
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            Savepoint buySavepoint = connection.setSavepoint("buySavepoint");

            User user = ((Cart) dbEntity).getUser();
            Part part = ((Cart) dbEntity).getPart();
            int cartId = ((Cart) dbEntity).getCartId();
            int partCount = ((Cart) dbEntity).getCount();
            int userId = user.getUserId();
            int partId = part.getPartId();
            Role userType = user.getRole();
            int roleId = userType.getRoleId();
            BigDecimal userBill = user.getBill();
            BigDecimal cost = part.getPrice();
            BigDecimal sum = cost.multiply(BigDecimal.valueOf(partCount));
            BigDecimal newUserBill = userBill.subtract(sum);

            PreparedStatement statement;
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
                statement.executeUpdate();

                int autoBillId;

                statement = connection.prepareStatement(BILL_ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, userId);
                statement.setBigDecimal(2, sum);
                statement.setInt(3, MINUS_BILL_INFO_ID);
                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                autoBillId = rs.getInt(1);

                statement = connection.prepareStatement(ORDER_ADD_SQL);
                statement.setInt(1, partId);
                statement.setInt(2, userId);
                statement.setBigDecimal(3, cost);
                statement.setInt(4, PROCESSING_CONDITION_ID);
                statement.setInt(5, partCount);
                statement.setBoolean(6, DEFAULT_IS_ACTIVE);
                statement.setInt(7, autoBillId);
                statement.execute();

                statement = connection.prepareStatement(CART_REMOVE_SQL);
                statement.setInt(1, cartId);
                statement.executeUpdate();

                connection.commit();

            } catch (SQLException e) {
                connection.rollback(buySavepoint);
                // TODO: 2019-07-29 log
            }

            connection.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RepositoryException("SQL error", e);
        }
        connectionPool.returnConnection(connection);
    }
}
