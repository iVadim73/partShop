package by.gvozdovich.partshop.model.specification.part;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT part_id, catalog_no, original_catalog_no, info, price, picture, wait, brand_id, stock_count, is_active FROM part";

    public PartAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all part specification fail", e);
        }
        return preparedStatement;
    }
}
