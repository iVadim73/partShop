package by.gvozdovich.partshop.model.specification.billinfo;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillInfoAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT * FROM bill_info";

    public BillInfoAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all bill info specification fail", e);
        }
        return preparedStatement;
    }
}
