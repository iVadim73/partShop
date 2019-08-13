package by.gvozdovich.partshop.model.specification.user;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserSpecificationByLoginAndPassword implements DbEntitySpecification {
    private String login;
    private String password;
    private static final String SQL = "SELECT user_id, login, password, email, phone, name, registration_date, discount, star, comment, bill, role_id, is_active FROM user WHERE login=(?) AND password=(?)";

    public UserSpecificationByLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }
    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
        } catch (SQLException e) {
            throw new SpecificationException("Login and password specification fail", e);
        }
        return preparedStatement;
    }
}