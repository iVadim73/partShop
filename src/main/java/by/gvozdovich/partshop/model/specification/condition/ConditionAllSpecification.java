package by.gvozdovich.partshop.model.specification.condition;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConditionAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT condition_id, name, info FROM conditions";

    public ConditionAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all condition specification fail", e);
        }
        return preparedStatement;
    }
}
