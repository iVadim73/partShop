package by.gvozdovich.partshop.model.specification.feedback;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT feedback_id, user_id, part_id, date, comment, star FROM feedback ORDER BY date DESC";

    public FeedbackAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all feedback specification fail", e);
        }
        return preparedStatement;
    }
}
