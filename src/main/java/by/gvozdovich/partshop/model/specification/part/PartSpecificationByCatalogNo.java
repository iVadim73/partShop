package by.gvozdovich.partshop.model.specification.part;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartSpecificationByCatalogNo implements DbEntitySpecification {
    private String catalogNo;
    private static final String SQL = "SELECT part_id, catalog_no, original_catalog_no, info, price, picture, wait, brand_id, stock_count, is_active FROM part WHERE catalog_no=(?)";

    public PartSpecificationByCatalogNo(String catalogNo) {
        this.catalogNo = catalogNo;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, catalogNo);
        } catch (SQLException e) {
            throw new SpecificationException("part catalogNo specification fail", e);
        }
        return preparedStatement;
    }
}
