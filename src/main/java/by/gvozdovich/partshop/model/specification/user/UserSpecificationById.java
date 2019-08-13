package by.gvozdovich.partshop.model.specification.user;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserSpecificationById implements DbEntitySpecification {
    private int userId;
    private static final String SQL = "SELECT user_id, login, password, email, phone, name, registration_date, discount, star, comment, bill, role_id, is_active FROM user WHERE user_id=(?)";

    public UserSpecificationById(int userId) {
        this.userId = userId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
        } catch (SQLException e) {
            throw new SpecificationException("user id specification fail", e);
        }
        return preparedStatement;
    }
}
