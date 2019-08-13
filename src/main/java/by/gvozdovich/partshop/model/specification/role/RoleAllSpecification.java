package by.gvozdovich.partshop.model.specification.role;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT role_id, type FROM role";

    public RoleAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all role specification fail", e);
        }
        return preparedStatement;
    }
}
