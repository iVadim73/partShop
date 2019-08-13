package by.gvozdovich.partshop.model.specification.part;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartSpecificationByCatalogNoAndBrandId implements DbEntitySpecification {
    private String catalogNo;
    private int brandId;
    private static final String SQL = "SELECT part_id, catalog_no, original_catalog_no, info, price, picture, wait, brand_id, stock_count, is_active FROM part WHERE catalog_no=(?) AND brand_id=(?)";

    public PartSpecificationByCatalogNoAndBrandId(String catalogNo, int brandId) {
        this.catalogNo = catalogNo;
        this.brandId = brandId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, catalogNo);
            preparedStatement.setInt(2, brandId);
        } catch (SQLException e) {
            throw new SpecificationException("part catalogNo and brandId specification fail", e);
        }
        return preparedStatement;
    }
}
