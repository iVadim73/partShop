package by.gvozdovich.partshop.model.specification.cart;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartSpecificationByUserId implements DbEntitySpecification {

    private int userId;
    private static final String SQL = "SELECT * FROM cart WHERE user_id=(?)";

    public CartSpecificationByUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
        } catch (SQLException e) {
            throw new SpecificationException("cart user id specification fail", e);
        }
        return preparedStatement;
    }
}
