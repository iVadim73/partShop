package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.BrandRepository;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.specification.*;
import by.gvozdovich.partshop.model.specification.brand.BrandAllSpecification;
import by.gvozdovich.partshop.model.specification.brand.BrandSpecificationById;
import by.gvozdovich.partshop.model.specification.brand.BrandSpecificationByName;
import by.gvozdovich.partshop.model.specification.brand.BrandSpecificationLikeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandService {
    private static BrandService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static BrandService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new BrandService();
        return instance;
    }

    private BrandService() {
        shopDataRepository = BrandRepository.getInstance();
    }

    public boolean add(String name, String country, String info, boolean isActive) throws ServiceException {
        DbEntitySpecification specification = new BrandSpecificationByName(name);
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.next()) {
                Brand brand = new Brand.Builder()
                        .withName(name)
                        .withCountry(country)
                        .withInfo(info)
                        .withIsActive(isActive)
                        .build();
                try {
                    shopDataRepository.addDBEntity(brand);
                } catch (RepositoryException e) {
                    throw new ServiceException("brand add fail", e);
                }
                logger.info("brand " + name + " added");
            } else {
                logger.warn("brand " + name + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("brand add fail", e);
        } catch (SQLException e) {
            throw new ServiceException("brand add SQL fail", e);
        }
        return true;
    }

    public boolean update(int brandId, String name, String country, String info, boolean isActive) throws ServiceException {
        Brand brand = new Brand.Builder()
                .withBrandId(brandId)
                .withName(name)
                .withCountry(country)
                .withInfo(info)
                .withIsActive(isActive)
                .build();

        return realUpdate(brand);
    }

    public boolean activateDeactivate(Brand brand) throws ServiceException {
        boolean isActive = !brand.getIsActive();
        Brand updatedBrand = takeBuilder(brand)
                .withIsActive(isActive)
                .build();
        return realUpdate(updatedBrand);
    }

    private Brand.Builder takeBuilder(Brand brand){
        Brand.Builder builder = new Brand.Builder();
        builder.withBrandId(brand.getBrandId())
                .withName(brand.getName())
                .withCountry(brand.getCountry())
                .withInfo(brand.getInfo())
                .withIsActive(brand.getIsActive());
        return builder;
    }

    private boolean realUpdate(Brand brand) throws ServiceException {
        String name = brand.getName();
        DbEntitySpecification specification = new BrandSpecificationByName(name);
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.isBeforeFirst()) {
                shopDataRepository.updateDBEntity(brand);
                logger.info("brand " + name + " updated");
            } else {
                while (resultSet.next()) {
                    int updateBrandId = brand.getBrandId();
                    int brandId = resultSet.getInt(ServiceConstant.BRAND_ID);
                    if (brandId == updateBrandId) {
                        shopDataRepository.updateDBEntity(brand);
                        logger.info("brand " + name + " updated");
                        return true;
                    }
                }
                logger.warn("brand " + name + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("brand update fail", e);
        } catch (SQLException e) {
            throw new ServiceException("brand update SQL fail", e);
        }
        return true;
    }

    public List<Brand> takeAllBrand() throws ServiceException {
        DbEntitySpecification specification = new BrandAllSpecification();
        return takeBrand(specification);
    }

    public List<Brand> takeBrand(String data) throws ServiceException {
        DbEntitySpecification specification = new BrandSpecificationLikeName(data);
        return takeBrand(specification);
    }

    public Brand takeBrandById(int id) throws ServiceException {
        DbEntitySpecification specification = new BrandSpecificationById(id);
        List<Brand> brands = takeBrand(specification);
        if (brands.isEmpty()) {
            throw new ServiceException("wrong brandId :" + id);
        }
        Brand brand = brands.get(0);
        return brand;
    }

    private List<Brand> takeBrand(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<Brand> brands = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take brand fail", e);
        }
        try {
            while (resultSet.next()) {
                Brand brand = new Brand.Builder()
                        .withBrandId(resultSet.getInt(ServiceConstant.BRAND_ID))
                        .withName(resultSet.getString(ServiceConstant.NAME))
                        .withCountry(resultSet.getString(ServiceConstant.COUNTRY))
                        .withInfo(resultSet.getString(ServiceConstant.INFO))
                        .withIsActive(resultSet.getInt(ServiceConstant.IS_ACTIVE) == 1)
                        .build();

                brands.add(brand);
            }
        } catch (SQLException e) {
            throw new ServiceException("take brand fail", e);
        }
        return brands;
    }
}
