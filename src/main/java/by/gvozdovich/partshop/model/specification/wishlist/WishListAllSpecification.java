package by.gvozdovich.partshop.model.specification.wishlist;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WishListAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT * FROM wish_list";

    public WishListAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all wishList specification fail", e);
        }
        return preparedStatement;
    }
}
