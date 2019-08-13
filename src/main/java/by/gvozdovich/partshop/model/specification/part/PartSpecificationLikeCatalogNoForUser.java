package by.gvozdovich.partshop.model.specification.part;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartSpecificationLikeCatalogNoForUser implements DbEntitySpecification {
    private String catalogNo;
    private static final String SQL = "SELECT part_id, catalog_no, original_catalog_no, info, price, picture, wait, brand_id, stock_count, is_active FROM part WHERE catalog_no LIKE (?) OR original_catalog_no LIKE (?) AND is_active=1";

    public PartSpecificationLikeCatalogNoForUser(String catalogNo) {
        this.catalogNo = catalogNo;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, "%" + catalogNo + "%");
            preparedStatement.setString(2, "%" + catalogNo + "%");
        } catch (SQLException e) {
            throw new SpecificationException("part like catalogNo for user specification fail", e);
        }
        return preparedStatement;
    }
}
