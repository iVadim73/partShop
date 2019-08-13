package by.gvozdovich.partshop.model.specification.user;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserSpecificationLikeLogin implements DbEntitySpecification {
    private String login;
    private static final String SQL = "SELECT user_id, login, password, email, phone, name, registration_date, discount, star, comment, bill, role_id, is_active FROM user WHERE login LIKE (?)";

    public UserSpecificationLikeLogin(String login) {
        this.login = login;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, "%" + login + "%");
        } catch (SQLException e) {
            throw new SpecificationException("user like login specification fail", e);
        }
        return preparedStatement;
    }
}
