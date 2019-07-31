package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.connectionpool.DbConnectionPool;
import by.gvozdovich.partshop.model.entity.Bill;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.RepositoryException;

import java.math.BigDecimal;
import java.sql.*;

public class BillRepository implements DataRepository {
    private DbConnectionPool connectionPool;
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
        connectionPool = getConnectionPool();
    }

    @Override
    public void addDBEntity(DbEntity dbEntity) throws RepositoryException { // TODO: 2019-07-29 норм???
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            Savepoint addBillSavepoint = connection.setSavepoint("addBillSavepoint");

            User user = ((Bill) dbEntity).getUser();
            int userId = user.getUserId();
            BigDecimal sum = ((Bill) dbEntity).getSum();
            BigDecimal userBill = user.getBill();
            BigDecimal newUserBill = userBill.add(sum);

            PreparedStatement statement;
            try {
                statement = connection.prepareStatement(BILL_ADD_SQL);
                statement.setInt(1, userId);
                statement.setBigDecimal(2, sum);
                statement.setInt(3, ((Bill) dbEntity).getBillInfo().getBillInfoId());
                statement.executeUpdate();

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
                statement.executeUpdate();

                connection.commit();

            } catch (SQLException e) {
                connection.rollback(addBillSavepoint);
                // TODO: 2019-07-29 log
            }

            connection.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RepositoryException("SQL error", e);
        }
        connectionPool.returnConnection(connection);
    }

//    @Override
//    public void addDBEntity(DbEntity dbEntity) throws RepositoryException {
//        Connection connection = getConnection();
//        PreparedStatement statement;
//        try {
//            statement = connection.prepareStatement(BILL_ADD_SQL);
//            statement.setInt(1, ((Bill) dbEntity).getUser().getUserId());
//            statement.setBigDecimal(2, ((Bill) dbEntity).getSum());
//            statement.setInt(3, ((Bill) dbEntity).getBillInfo().getBillInfoId());
//            statement.execute();
//        } catch (SQLException e) {
//            throw new RepositoryException("add bill", e);
//        }
//        connectionPool.returnConnection(connection);
//    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(BILL_UPDATE_SQL);
            statement.setInt(1, ((Bill) dbEntity).getUser().getUserId());
            statement.setBigDecimal(2, ((Bill) dbEntity).getSum());
            statement.setInt(3, ((Bill) dbEntity).getBillInfo().getBillInfoId());
            statement.setInt(4, ((Bill) dbEntity).getBillId());
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
            statement = connection.prepareStatement(BILL_REMOVE_SQL);
            statement.setInt(1, ((Bill) dbEntity).getBillId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        }
        connectionPool.returnConnection(connection);
    }
}
