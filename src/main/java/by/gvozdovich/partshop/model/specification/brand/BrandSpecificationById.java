package by.gvozdovich.partshop.model.specification.brand;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BrandSpecificationById implements DbEntitySpecification {
    private int id;
    private static final String SQL = "SELECT brand_id, name, country, info, is_active FROM brand WHERE brand_id=(?)";

    public BrandSpecificationById(int id) {
        this.id = id;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            throw new SpecificationException("brand id specification fail", e);
        }
        return preparedStatement;
    }
}
