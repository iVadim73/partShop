package by.gvozdovich.partshop.model.specification.feedback;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackSpecificationById implements DbEntitySpecification {
    private int feedbackId;
    private static final String SQL = "SELECT * FROM feedback WHERE feedback_id=(?)";

    public FeedbackSpecificationById(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, feedbackId);
        } catch (SQLException e) {
            throw new SpecificationException("feedback id specification fail", e);
        }
        return preparedStatement;
    }
}
