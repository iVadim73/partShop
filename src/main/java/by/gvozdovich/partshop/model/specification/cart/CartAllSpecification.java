package by.gvozdovich.partshop.model.specification.cart;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT * FROM cart";

    public CartAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all cart specification fail", e);
        }
        return preparedStatement;
    }
}
