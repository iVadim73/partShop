package by.gvozdovich.partshop.model.specification.feedback;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackSpecificationByPartIdLimit implements DbEntitySpecification {
    private static final int COUNT_PER_PAGE = 10;
    private int partId;
    private int start;
    private int end;
    private static final String SQL = "SELECT feedback_id, user_id, part_id, date, comment, star FROM feedback WHERE part_id=(?) ORDER BY date DESC LIMIT ?, ?";

    public FeedbackSpecificationByPartIdLimit(int partId, int page) {
        this.partId = partId;
        this.start = COUNT_PER_PAGE * (page - 1);
        this.end = (COUNT_PER_PAGE * page) + 1;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, partId);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, end);
        } catch (SQLException e) {
            throw new SpecificationException("part id specification fail", e);
        }
        return preparedStatement;
    }
}
