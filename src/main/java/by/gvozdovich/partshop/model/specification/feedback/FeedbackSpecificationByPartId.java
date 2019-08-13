package by.gvozdovich.partshop.model.specification.feedback;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackSpecificationByPartId implements DbEntitySpecification {

    private int partId;
    private static final String SQL = "SELECT feedback_id, user_id, part_id, date, comment, star FROM feedback WHERE part_id=(?) ORDER BY date DESC";

    public FeedbackSpecificationByPartId(int partId) {
        this.partId = partId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, partId);
        } catch (SQLException e) {
            throw new SpecificationException("part id specification fail", e);
        }
        return preparedStatement;
    }
}
