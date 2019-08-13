package by.gvozdovich.partshop.model.specification.billinfo;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillInfoSpecificationById implements DbEntitySpecification {
    private int billInfoId;
    private static final String SQL = "SELECT bill_info_id, info FROM bill_info WHERE bill_info_id=(?)";

    public BillInfoSpecificationById(int billInfoId) {
        this.billInfoId = billInfoId;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, billInfoId);
        } catch (SQLException e) {
            throw new SpecificationException("bill info id specification fail", e);
        }
        return preparedStatement;
    }
}
