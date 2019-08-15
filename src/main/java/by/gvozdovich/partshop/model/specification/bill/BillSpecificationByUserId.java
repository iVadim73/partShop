package by.gvozdovich.partshop.model.specification.bill;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillSpecificationByUserId implements DbEntitySpecification {
    private static final String SQL = "SELECT bill_id, user_id, sum, bill_info_id, date FROM bill WHERE user_id=(?) ORDER BY date DESC LIMIT ?, ?";
    private static final int COUNT_PER_PAGE = 10;
    private int userId;
    private int start;
    private int end;

    public BillSpecificationByUserId(int userId, int page) {
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
            throw new SpecificationException("bill user id specification fail", e);
        }
        return preparedStatement;
    }
}
