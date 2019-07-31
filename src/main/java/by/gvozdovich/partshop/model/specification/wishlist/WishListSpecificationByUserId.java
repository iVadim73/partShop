package by.gvozdovich.partshop.model.specification.wishlist;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WishListSpecificationByUserId implements DbEntitySpecification {

    private int userId;
    private static final String SQL = "SELECT * FROM wish_list WHERE user_id=(?)";

    public WishListSpecificationByUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
        } catch (SQLException e) {
            throw new SpecificationException("wish list user id specification fail", e);
        }
        return preparedStatement;
    }
}
