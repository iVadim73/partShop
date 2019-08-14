package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * class that interacts with the database and accumulates in itself all methods to add/update/remove or query the
 * {@link Brand} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class BrandRepository implements DataRepository {
    private static Logger logger = LogManager.getLogger();
    private static BrandRepository instance;
    private static final String ADD_SQL = "INSERT INTO brand (name, country, info, is_active) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE brand SET name=(?), country=(?), info=(?), is_active=(?) WHERE brand_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM brand WHERE brand_id=(?)";

    public static BrandRepository getInstance() {
        if(instance == null) {
            instance = new BrandRepository();
        }
        return instance;
    }

    private BrandRepository() {

    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ((Brand) dbEntity).getName());
            statement.setString(2, ((Brand) dbEntity).getCountry());
            statement.setString(3, ((Brand) dbEntity).getInfo());
            statement.setBoolean(4, ((Brand) dbEntity).getIsActive());
            statement.execute();
            logger.debug("brand added :" + dbEntity);

            rs = statement.getGeneratedKeys();
            rs.next();
            int autoId = rs.getInt(1);
            return autoId;
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("add brand", e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, ((Brand) dbEntity).getName());
            statement.setString(2, ((Brand) dbEntity).getCountry());
            statement.setString(3, ((Brand) dbEntity).getInfo());
            statement.setBoolean(4, ((Brand) dbEntity).getIsActive());
            statement.setInt(5, ((Brand) dbEntity).getBrandId());
            statement.execute();
            logger.debug("brand updated :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("update", e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(REMOVE_SQL);
            statement.setInt(1, ((Brand) dbEntity).getBrandId());
            statement.execute();
            logger.debug("brand removed :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("remove", e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException {
        ResultSet resultSet = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<DbEntity> brandList = new ArrayList<>();
        try {
            statement = specification.specified(connection);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Brand brand = new Brand.Builder()
                        .withBrandId(resultSet.getInt(ServiceConstant.BRAND_ID))
                        .withName(resultSet.getString(ServiceConstant.NAME))
                        .withCountry(resultSet.getString(ServiceConstant.COUNTRY))
                        .withInfo(resultSet.getString(ServiceConstant.INFO))
                        .withIsActive(resultSet.getInt(ServiceConstant.IS_ACTIVE) == 1)
                        .build();

                brandList.add(brand);
            }
        } catch (SpecificationException e) {
            logger.error("SpecificationException :" + e);
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("Repository execute fail", e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
        logger.debug("brand query :" + brandList);
        return brandList;
    }
}
