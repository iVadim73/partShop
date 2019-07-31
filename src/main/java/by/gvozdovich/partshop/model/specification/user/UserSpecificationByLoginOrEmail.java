package by.gvozdovich.partshop.model.specification.user;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserSpecificationByLoginOrEmail implements DbEntitySpecification {
    private String login;
    private String email;
    private static final String SQL = "SELECT user_id FROM user WHERE login=(?) OR email=(?)";

    public UserSpecificationByLoginOrEmail(String login, String email) {
        this.login = login;
        this.email = email;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, email);
        } catch (SQLException e) {
            throw new SpecificationException("Login and email specification fail", e);
        }
        return preparedStatement;
    }
}
