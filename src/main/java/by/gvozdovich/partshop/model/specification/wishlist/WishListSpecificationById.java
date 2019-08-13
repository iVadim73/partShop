package by.gvozdovich.partshop.model.specification.wishlist;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WishListSpecificationById implements DbEntitySpecification {
    private int wishListId;
    private static final String SQL = "SELECT wish_list_id, user_id, part_id FROM wish_list WHERE wish_list_id=(?)";

    public WishListSpecificationById(int wishListId) {
        this.wishListId = wishListId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, wishListId);
        } catch (SQLException e) {
            throw new SpecificationException("wishList id specification fail", e);
        }
        return preparedStatement;
    }
}
