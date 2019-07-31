package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.BillInfo;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillInfoService {
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
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.next()) {
                BillInfo billInfo = new BillInfo.Builder()
                        .withInfo(info)
                        .build();
                try {
                    shopDataRepository.addDBEntity(billInfo);
                } catch (RepositoryException e) {
                    throw new ServiceException("bill info add fail", e);
                }
                logger.info("bill info " + info + " added");
            } else {
                logger.warn("bill info " + info + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("bill info add fail", e);
        } catch (SQLException e) {
            throw new ServiceException("bill info add SQL fail", e);
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
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.isBeforeFirst()) {
                shopDataRepository.updateDBEntity(billInfo);
                logger.info("bill info " + info + " updated");
            } else {
                logger.warn("bill info " + info + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("bill info update fail", e);
        } catch (SQLException e) {
            throw new ServiceException("bill info update SQL fail", e);
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
        BillInfo billInfo = billInfoList.get(0);
        return billInfo;
    }

    private List<BillInfo> takeBillInfo(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<BillInfo> billInfoList = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take bill info fail", e);
        }
        try {
            while (resultSet.next()) {
                BillInfo billInfo = new BillInfo.Builder()
                        .withBillInfoId(resultSet.getInt(ServiceConstant.BILL_INFO_ID))
                        .withInfo(resultSet.getString(ServiceConstant.INFO))
                        .build();

                billInfoList.add(billInfo);
            }
        } catch (SQLException e) {
            throw new ServiceException("take bill info fail", e);
        }
        return billInfoList;
    }
}
