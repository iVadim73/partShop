package by.gvozdovich.partshop.model.specification.part;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartAllLimitSpecificationForUser implements DbEntitySpecification {
    private static final int COUNT_PER_PAGE = 10;
    private int start;
    private int end;
    private static final String SQL = "SELECT part_id, catalog_no, original_catalog_no, info, price, picture, wait, brand_id, stock_count, is_active FROM part WHERE is_active=1 AND brand_id IN (SELECT brand_id FROM brand WHERE is_active=1) LIMIT ?, ?";

    public PartAllLimitSpecificationForUser(int page) {
        this.start = COUNT_PER_PAGE * (page - 1) + 1;
        this.end = (COUNT_PER_PAGE * page) + 1;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, end);
        } catch (SQLException e) {
            throw new SpecificationException("all part specification fail", e);
        }
        return preparedStatement;
    }
}
