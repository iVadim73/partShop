package by.gvozdovich.partshop.model.specification.part;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartAllForUserSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT part_id, catalog_no, original_catalog_no, info, price, picture, wait, brand_id, stock_count, is_active FROM part WHERE is_active=1 AND brand_id IN (SELECT brand_id FROM brand WHERE is_active=1)";

    public PartAllForUserSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all part for user specification fail", e);
        }
        return preparedStatement;
    }
}
