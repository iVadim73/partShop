package by.gvozdovich.partshop.model.specification.condition;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConditionSpecificationByName implements DbEntitySpecification {
    private String name;
    private static final String SQL = "SELECT condition_id, name, info FROM conditions WHERE name=(?)";

    public ConditionSpecificationByName(String name) {
        this.name = name;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            throw new SpecificationException("condition specification fail", e);
        }
        return preparedStatement;
    }
}
