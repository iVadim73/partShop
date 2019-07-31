package by.gvozdovich.partshop.model.specification.brand;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BrandSpecificationLikeName implements DbEntitySpecification {
    private String name;
    private static final String SQL = "SELECT * FROM brand WHERE name LIKE (?)";

    public BrandSpecificationLikeName(String name) {
        this.name = name;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, "%" + name + "%");
        } catch (SQLException e) {
            throw new SpecificationException("brand like name specification fail", e);
        }
        return preparedStatement;
    }
}
