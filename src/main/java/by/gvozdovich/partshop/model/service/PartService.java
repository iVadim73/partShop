package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.entity.DbEntity;
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
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link Part} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class PartService implements Service {
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

    public int add(String catalogNo, String originalCatalogNo, String info, BigDecimal price, String pictureUrl,
                       int wait, Brand brand, int stockCount, boolean isActive) throws ServiceException {
        int partId;
        int brand_id = brand.getBrandId();
        DbEntitySpecification specification = new PartSpecificationByCatalogNoAndBrandId(catalogNo, brand_id);
        List<Part> partList = takePart(specification);
        String usefulCatalogNo = takeUsefulData(catalogNo);
        String usefulOriginalCatalogNo = takeUsefulData(originalCatalogNo);
        if (partList.isEmpty()) {
            Part part = new Part.Builder()
                    .withCatalogNo(usefulCatalogNo)
                    .withOriginalCatalogNo(usefulOriginalCatalogNo)
                    .withInfo(info)
                    .withPrice(price)
                    .withWait(wait)
                    .withBrand(brand)
                    .withStockCount(stockCount)
                    .withIsActive(isActive)
                    .build();
            try {
                partId = shopDataRepository.addDBEntity(part);
            } catch (RepositoryException e) {
                throw new ServiceException("part add fail", e);
            }
            logger.info("part " + part + " added");
        } else {
            logger.warn("part " + catalogNo + " brand " + brand.getName() + " already added");
            partId = -1;
        }
        return partId;
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

    public boolean updatePicture(Part part, String pictureUrl) throws ServiceException {
        Part updatedPart = takeBuilder(part)
                .withPictureUrl(pictureUrl)
                .build();
        return realUpdate(updatedPart);
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
                .withPictureUrl(order.getPictureUrl())
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
            List<Part> partList = takePart(specification);
            if (partList.isEmpty()) {
                shopDataRepository.updateDBEntity(part);
                logger.info("part " + catalogNo + " updated");
            } else {
                int updatePartId = part.getPartId();
                for (Part currentPart: partList) {
                    int partId = currentPart.getPartId();
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
        }
        return true;
    }

    public List<Part> takeAllPartLimit(int pageCount) throws ServiceException {
        DbEntitySpecification specification = new PartAllLimitSpecification(pageCount);
        return takePart(specification);
    }

    public List<Part> takeAllPartLimitForUser(int pageCount) throws ServiceException {
        DbEntitySpecification specification = new PartAllLimitSpecificationForUser(pageCount);
        return takePart(specification);
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
        return parts.get(0);
    }

    private List<Part> takePart(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<Part> partList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            partList.add((Part) dbEntity);
        }
        return partList;
    }

    private String takeUsefulData(String data) {
        return data.replaceAll(USEFUL_DATA_REGEX, "");
    }
}
