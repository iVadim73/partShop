package by.gvozdovich.partshop.model.specification.order;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT order_id, part_id, user_id, order_date, cost, condition_id, condition_date, is_active, part_count, bill_id FROM orders ORDER BY condition_date DESC LIMIT ?, ?";
    private static final int COUNT_PER_PAGE = 10;
    private int start;
    private int end;

    public OrderAllSpecification(int page) {
        this.start = COUNT_PER_PAGE * (page - 1);
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
            throw new SpecificationException("all order specification fail", e);
        }
        return preparedStatement;
    }
}
