package by.gvozdovich.partshop.model.specification.order;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderSpecificationByUserId implements DbEntitySpecification {
    private static final String SQL = "SELECT order_id, part_id, user_id, order_date, cost, condition_id, condition_date, is_active, part_count, bill_id FROM orders WHERE user_id=(?) ORDER BY condition_date DESC LIMIT ?, ?";
    private static final int COUNT_PER_PAGE = 10;
    private int start;
    private int end;
    private int userId;

    public OrderSpecificationByUserId(int userId, int page) {
        this.userId = userId;
        this.start = COUNT_PER_PAGE * (page - 1);
        this.end = (COUNT_PER_PAGE * page) + 1;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, end);
        } catch (SQLException e) {
            throw new SpecificationException("order user id specification fail", e);
        }
        return preparedStatement;
    }
}
