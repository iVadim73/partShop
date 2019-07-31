package by.gvozdovich.partshop.model.specification.order;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderSpecificationByUserId implements DbEntitySpecification {

    private int userId;
    private static final String SQL = "SELECT * FROM orders WHERE user_id=(?)";

    public OrderSpecificationByUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
        } catch (SQLException e) {
            throw new SpecificationException("order user id specification fail", e);
        }
        return preparedStatement;
    }
}
