package by.gvozdovich.partshop.model.specification.role;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleSpecificationByType implements DbEntitySpecification {
    private String type;
    private static final String SQL = "SELECT role_id, type FROM role WHERE type=(?)";

    public RoleSpecificationByType(String type) {
        this.type = type;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, type);
        } catch (SQLException e) {
            throw new SpecificationException("role specification fail", e);
        }
        return preparedStatement;
    }
}
