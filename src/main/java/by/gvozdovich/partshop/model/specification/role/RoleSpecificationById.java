package by.gvozdovich.partshop.model.specification.role;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleSpecificationById implements DbEntitySpecification {
    private int roleId;
    private static final String SQL = "SELECT role_id, type FROM role WHERE role_id=(?)";

    public RoleSpecificationById(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, roleId);
        } catch (SQLException e) {
            throw new SpecificationException("role id specification fail", e);
        }
        return preparedStatement;
    }
}
