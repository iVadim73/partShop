package by.gvozdovich.partshop.model.specification.brand;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BrandSpecificationByName implements DbEntitySpecification {
    private String name;
    private static final String SQL = "SELECT brand_id FROM brand WHERE name=(?)";

    public BrandSpecificationByName(String name) {
        this.name = name;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            throw new SpecificationException("brand name specification fail", e);
        }
        return preparedStatement;
    }
}
