package by.gvozdovich.partshop.model.specification.billinfo;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillInfoSpecificationByInfo implements DbEntitySpecification {
    private String info;
    private static final String SQL = "SELECT bill_info_id FROM bill_info WHERE info=(?)";

    public BillInfoSpecificationByInfo(String info) {
        this.info = info;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SpecificationException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, info);
        } catch (SQLException e) {
            throw new SpecificationException("info specification fail", e);
        }
        return preparedStatement;
    }
}
