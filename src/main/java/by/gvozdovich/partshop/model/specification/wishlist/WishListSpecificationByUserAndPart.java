package by.gvozdovich.partshop.model.specification.wishlist;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WishListSpecificationByUserAndPart implements DbEntitySpecification {
    private int userId;
    private int partId;
    private static final String SQL = "SELECT wish_list_id, user_id, part_id FROM wish_list WHERE user_id=(?) AND part_id=(?)";

    public WishListSpecificationByUserAndPart(int userId, int partId) {
        this.userId = userId;
        this.partId = partId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, partId);
        } catch (SQLException e) {
            throw new SpecificationException("user and part specification fail", e);
        }
        return preparedStatement;
    }
}
