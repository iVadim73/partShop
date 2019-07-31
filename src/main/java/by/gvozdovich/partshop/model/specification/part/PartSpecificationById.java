package by.gvozdovich.partshop.model.specification.part;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartSpecificationById implements DbEntitySpecification {
    private int id;
    private static final String SQL = "SELECT * FROM part WHERE part_id=(?)";

    public PartSpecificationById(int id) {
        this.id = id;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            throw new SpecificationException("part id specification fail", e);
        }
        return preparedStatement;
    }
}
