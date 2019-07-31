package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.PartRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.part.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartService {
    private static PartService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;
    private static final String USEFUL_DATA_REGEX = "[\\W_]{1}";

    public static PartService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new PartService();
        return instance;
    }

    private PartService() {
        shopDataRepository = PartRepository.getInstance();
    }

    public boolean add(String catalogNo, String originalCatalogNo, String info, BigDecimal price, String pictureUrl,
                       int wait, Brand brand, int stockCount, boolean isActive) throws ServiceException {
        int brand_id = brand.getBrandId();
        DbEntitySpecification specification = new PartSpecificationByCatalogNoAndBrandId(catalogNo, brand_id);
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            String usefulCatalogNo = takeUsefulData(catalogNo);
            String usefulOriginalCatalogNo = takeUsefulData(originalCatalogNo);
            if (!resultSet.next()) {
                Part part = new Part.Builder()
                        .withCatalogNo(usefulCatalogNo)
                        .withOriginalCatalogNo(usefulOriginalCatalogNo)
                        .withInfo(info)
                        .withPrice(price)
                        .withPictureUrl(pictureUrl)
                        .withWait(wait)
                        .withBrand(brand)
                        .withStockCount(stockCount)
                        .withIsActive(isActive)
                        .build();
                try {
                    shopDataRepository.addDBEntity(part);
                } catch (RepositoryException e) {
                    throw new ServiceException("part add fail", e);
                }
                logger.info("part " + part + " added");
            } else {
                logger.warn("part " + catalogNo + " brand " + brand.getName() + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("part add fail", e);
        } catch (SQLException e) {
            throw new ServiceException("part add SQL fail", e);
        }
        return true;
    }

    public boolean update(int partId, String catalogNo, String originalCatalogNo, String info, BigDecimal price,
                          String pictureUrl, int wait, Brand brand, int stockCount, boolean isActive) throws ServiceException {
        Part part = new Part.Builder()
                .withPartId(partId)
                .withCatalogNo(catalogNo)
                .withOriginalCatalogNo(originalCatalogNo)
                .withInfo(info)
                .withPrice(price)
                .withPictureUrl(pictureUrl)
                .withWait(wait)
                .withBrand(brand)
                .withStockCount(stockCount)
                .withIsActive(isActive)
                .build();

        return realUpdate(part);
    }

    public boolean activateDeactivate(Part part) throws ServiceException {
        boolean isActive = !part.getIsActive();
        Part updatedPart = takeBuilder(part)
                .withIsActive(isActive)
                .build();
        return realUpdate(updatedPart);
    }

    private Part.Builder takeBuilder(Part order){
        Part.Builder builder = new Part.Builder();
        builder.withPartId(order.getPartId())
                .withCatalogNo(order.getCatalogNo())
                .withOriginalCatalogNo(order.getOriginalCatalogNo())
                .withInfo(order.getInfo())
                .withPrice(order.getPrice())
                .withPictureUrl(order.getPictureURL())
                .withWait(order.getWait())
                .withBrand(order.getBrand())
                .withStockCount(order.getStockCount())
                .withIsActive(order.getIsActive());
        return builder;
    }

    private boolean realUpdate(Part part) throws ServiceException {
        String catalogNo = part.getCatalogNo();
        int brand_id = part.getBrand().getBrandId();
        DbEntitySpecification specification = new PartSpecificationByCatalogNoAndBrandId(catalogNo, brand_id);
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.isBeforeFirst()) {
                shopDataRepository.updateDBEntity(part);
                logger.info("part " + catalogNo + " updated");
            } else {
                while (resultSet.next()) {
                    int updatePartId = part.getPartId();
                    int partId = resultSet.getInt(ServiceConstant.PART_ID);
                    if (partId == updatePartId) {
                        shopDataRepository.updateDBEntity(part);
                        logger.info("part " + catalogNo + " updated");
                        return true;
                    }
                }
                logger.warn("part " + catalogNo + " brand " + part.getBrand().getName() + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("part update fail", e);
        } catch (SQLException e) {
            throw new ServiceException("part update SQL fail", e);
        }
        return true;
    }

    public List<Part> takeAllPart() throws ServiceException {
        DbEntitySpecification specification = new PartAllSpecification();
        return takePart(specification);
    }

    public List<Part> takeAllPartForUser() throws ServiceException {
        DbEntitySpecification specification = new PartAllForUserSpecification();
        return takePart(specification);
    }

    public List<Part> takePart(String data) throws ServiceException {
        String usefulData = takeUsefulData(data);
        DbEntitySpecification specification = new PartSpecificationLikeCatalogNo(usefulData);
        return takePart(specification);
    }

    public List<Part> takePartForUser(String data) throws ServiceException {
        String usefulData = takeUsefulData(data);
        DbEntitySpecification specification = new PartSpecificationLikeCatalogNoForUser(usefulData);
        return takePart(specification);
    }

    public Part takePartById(int id) throws ServiceException {
        DbEntitySpecification specification = new PartSpecificationById(id);
        List<Part> parts = takePart(specification);
        if (parts.isEmpty()) {
            throw new ServiceException("wrong partId :" + id);
        }
        Part part = parts.get(0);
        return part;
    }

    private List<Part> takePart(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<Part> parts = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take part fail", e);
        }
        try {
            while (resultSet.next()) {
                int brandId = resultSet.getInt(ServiceConstant.BRAND_ID);
                Brand brand = BrandService.getInstance().takeBrandById(brandId);

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

                parts.add(part);
            }
        } catch (SQLException e) {
            throw new ServiceException("take part fail", e);
        }
        return parts;
    }

    private String takeUsefulData(String data) {
        return data.replaceAll(USEFUL_DATA_REGEX, "");
    }
}
