package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.service.BrandService;
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
 * {@link Part} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class PartRepository implements DataRepository {
    private static Logger logger = LogManager.getLogger();
    private static PartRepository instance;
    private static final String ADD_SQL = "INSERT INTO part (catalog_no, original_catalog_no, info, price, wait, brand_id, stock_count, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE part SET catalog_no=(?), original_catalog_no=(?), info=(?), price=(?), picture=(?), wait=(?), brand_id=(?), stock_count=(?), is_active=(?) WHERE part_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM part WHERE part_id=(?)";

    public static PartRepository getInstance() {
        if(instance == null) {
            instance = new PartRepository();
        }
        return instance;
    }

    private PartRepository() {

    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ((Part) dbEntity).getCatalogNo());
            statement.setString(2, ((Part) dbEntity).getOriginalCatalogNo());
            statement.setString(3, ((Part) dbEntity).getInfo());
            statement.setBigDecimal(4, ((Part) dbEntity).getPrice());
            statement.setInt(5, ((Part) dbEntity).getWait());
            statement.setInt(6, ((Part) dbEntity).getBrand().getBrandId());
            statement.setInt(7, ((Part) dbEntity).getStockCount());
            statement.setBoolean(8, ((Part) dbEntity).getIsActive());
            statement.execute();
            logger.debug("part added :" + dbEntity);

            rs = statement.getGeneratedKeys();
            rs.next();
            int autoBillId = rs.getInt(1);
            return autoBillId;
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("add part", e);
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
            statement.setString(1, ((Part) dbEntity).getCatalogNo());
            statement.setString(2, ((Part) dbEntity).getOriginalCatalogNo());
            statement.setString(3, ((Part) dbEntity).getInfo());
            statement.setBigDecimal(4, ((Part) dbEntity).getPrice());
            statement.setString(5, ((Part) dbEntity).getPictureUrl());
            statement.setInt(6, ((Part) dbEntity).getWait());
            statement.setInt(7, ((Part) dbEntity).getBrand().getBrandId());
            statement.setInt(8, ((Part) dbEntity).getStockCount());
            statement.setBoolean(9, ((Part) dbEntity).getIsActive());
            statement.setInt(10, ((Part) dbEntity).getPartId());
            statement.execute();
            logger.debug("part updated :" + dbEntity);
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
            statement.setInt(1, ((Part) dbEntity).getPartId());
            statement.execute();
            logger.debug("part removed :" + dbEntity);
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
        List<DbEntity> partList = new ArrayList<>();
        try {
            statement = specification.specified(connection);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int brandId = resultSet.getInt(ServiceConstant.BRAND_ID);
                Brand brand;
                try {
                    brand = BrandService.getInstance().takeBrandById(brandId);
                } catch (ServiceException e) {
                    logger.error("ServiceException :" + e);
                    throw new RepositoryException("take brand fail", e);
                }

                Part part = new Part.Builder()
                        .withPartId(resultSet.getInt(ServiceConstant.PART_ID))
                        .withCatalogNo(resultSet.getString(ServiceConstant.CATALOG_NO))
                        .withOriginalCatalogNo(resultSet.getString(ServiceConstant.ORIGINAL_CATALOG_NO))
                        .withInfo(resultSet.getString(ServiceConstant.INFO))
                        .withPrice(resultSet.getBigDecimal(ServiceConstant.PRICE))
                        .withPictureUrl(resultSet.getString(ServiceConstant.PICTURE))
                        .withWait(resultSet.getInt(ServiceConstant.WAIT))
                        .withBrand(brand)
                        .withStockCount(resultSet.getInt(ServiceConstant.STOCK_COUNT))
                        .withIsActive(resultSet.getInt(ServiceConstant.IS_ACTIVE) == 1)
                        .build();

                partList.add(part);
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
        logger.debug("part query :" + partList);
        return partList;
    }
}
