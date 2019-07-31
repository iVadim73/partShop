package by.gvozdovich.partshop.model.specification.brand;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BrandAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT * FROM brand";

    public BrandAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all brand specification fail", e);
        }
        return preparedStatement;
    }
}
