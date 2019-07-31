package by.gvozdovich.partshop.model.specification.cart;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartSpecificationByPartIdAndUserId implements DbEntitySpecification {
    private int partId;
    private int userId;
    private static final String SQL = "SELECT * FROM cart WHERE part_id=(?) AND user_id=(?)";

    public CartSpecificationByPartIdAndUserId(int partId, int userId) {
        this.partId = partId;
        this.userId = userId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, partId);
            preparedStatement.setInt(2, userId);
        } catch (SQLException e) {
            throw new SpecificationException("part id and user id specification fail", e);
        }
        return preparedStatement;
    }
}
