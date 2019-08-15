package by.gvozdovich.partshop.model.specification.bill;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillSpecificationById implements DbEntitySpecification {
    private static final String SQL = "SELECT bill_id, user_id, sum, bill_info_id, date FROM bill WHERE bill_id=(?)";
    private int billId;

    public BillSpecificationById(int billId) {
        this.billId = billId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, billId);
        } catch (SQLException e) {
            throw new SpecificationException("bill id specification fail", e);
        }
        return preparedStatement;
    }
}
