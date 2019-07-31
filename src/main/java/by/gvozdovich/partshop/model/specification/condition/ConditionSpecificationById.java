package by.gvozdovich.partshop.model.specification.condition;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConditionSpecificationById implements DbEntitySpecification {
    private int conditionId;
    private static final String SQL = "SELECT * FROM conditions WHERE condition_id=(?)";

    public ConditionSpecificationById(int conditionId) {
        this.conditionId = conditionId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, conditionId);
        } catch (SQLException e) {
            throw new SpecificationException("condition id specification fail", e);
        }
        return preparedStatement;
    }
}
