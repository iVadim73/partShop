package by.gvozdovich.partshop.model.specification.cart;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartSpecificationById implements DbEntitySpecification {
    private int cartId;
    private static final String SQL = "SELECT * FROM cart WHERE cart_id=(?)";

    public CartSpecificationById(int cartId) {
        this.cartId = cartId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, cartId);
        } catch (SQLException e) {
            throw new SpecificationException("cart id specification fail", e);
        }
        return preparedStatement;
    }
}
