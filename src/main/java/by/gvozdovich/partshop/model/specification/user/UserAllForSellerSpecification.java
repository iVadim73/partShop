package by.gvozdovich.partshop.model.specification.user;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAllForSellerSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT user_id, login, password, email, phone, name, registration_date, discount, star, comment, bill, role_id, is_active FROM user WHERE role_id = 2";

    public UserAllForSellerSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all user for seller specification fail", e);
        }
        return preparedStatement;
    }
}
