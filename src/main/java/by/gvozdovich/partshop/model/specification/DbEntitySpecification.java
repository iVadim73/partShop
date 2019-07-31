package by.gvozdovich.partshop.model.specification;

import by.gvozdovich.partshop.model.exception.SpecificationException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public interface DbEntitySpecification {
    PreparedStatement specified(Connection connection) throws SpecificationException;
}
