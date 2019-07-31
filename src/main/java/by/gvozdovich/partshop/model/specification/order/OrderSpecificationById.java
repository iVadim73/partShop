package by.gvozdovich.partshop.model.specification.order;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderSpecificationById implements DbEntitySpecification {
    private int orderId;
    private static final String SQL = "SELECT * FROM orders WHERE order_id=(?)";

    public OrderSpecificationById(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, orderId);
        } catch (SQLException e) {
            throw new SpecificationException("order id specification fail", e);
        }
        return preparedStatement;
    }
}
