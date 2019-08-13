package by.gvozdovich.partshop.model.specification.bill;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillAllSpecification implements DbEntitySpecification {
    private static final String SQL = "SELECT bill_id, user_id, sum, bill_info_id, date FROM bill ORDER BY date DESC";

    public BillAllSpecification() {
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new SpecificationException("all bill specification fail", e);
        }
        return preparedStatement;
    }
}
