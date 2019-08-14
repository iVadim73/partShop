package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.BillInfo;
import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.BillInfoRepository;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.billinfo.BillInfoAllSpecification;
import by.gvozdovich.partshop.model.specification.billinfo.BillInfoSpecificationById;
import by.gvozdovich.partshop.model.specification.billinfo.BillInfoSpecificationByInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link BillInfo} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class BillInfoService implements Service {
    private static BillInfoService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static BillInfoService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new BillInfoService();
        return instance;
    }

    private BillInfoService() {
        shopDataRepository = BillInfoRepository.getInstance();
    }

    public boolean add(String info) throws ServiceException {
        DbEntitySpecification specification = new BillInfoSpecificationByInfo(info);
        List<BillInfo> billInfoList = takeBillInfo(specification);
        if (billInfoList.isEmpty()) {
            BillInfo billInfo = new BillInfo.Builder()
                    .withInfo(info)
                    .build();
            try {
                int billId = shopDataRepository.addDBEntity(billInfo);
            } catch (RepositoryException e) {
                throw new ServiceException("bill info add fail", e);
            }
            logger.info("bill info " + info + " added");
        } else {
            logger.warn("bill info " + info + " already added");
            return false;
        }
        return true;
    }

    public boolean update(int billInfoId, String info) throws ServiceException {
        BillInfo billInfo = new BillInfo.Builder()
                .withBillInfoId(billInfoId)
                .withInfo(info)
                .build();

        return realUpdate(billInfo);
    }

    private boolean realUpdate(BillInfo billInfo) throws ServiceException {
        String info = billInfo.getInfo();
        DbEntitySpecification specification = new BillInfoSpecificationByInfo(info);
        try {
            List<BillInfo> billInfoList = takeBillInfo(specification);
            if (billInfoList.isEmpty()) {
                shopDataRepository.updateDBEntity(billInfo);
                logger.info("bill info " + info + " updated");
            } else {
                logger.warn("bill info " + info + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            logger.error("bill info " + info + " update fail");
            throw new ServiceException("bill info update fail", e);
        }
        return true;
    }

    public List<BillInfo> takeAllBillInfo() throws ServiceException {
        DbEntitySpecification specification = new BillInfoAllSpecification();
        return takeBillInfo(specification);
    }

    public BillInfo takeBillInfoById(int id) throws ServiceException {
        DbEntitySpecification specification = new BillInfoSpecificationById(id);
        List<BillInfo> billInfoList = takeBillInfo(specification);
        if (billInfoList.isEmpty()) {
            throw new ServiceException("wrong billInfoId :" + id);
        }
        return billInfoList.get(0);
    }

    private List<BillInfo> takeBillInfo(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<BillInfo> billInfoList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            billInfoList.add((BillInfo) dbEntity);
        }
        logger.info("take bill info " + specification + " successful");
        return billInfoList;
    }
}
