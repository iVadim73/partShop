package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.BillRepository;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.bill.BillAllSpecification;
import by.gvozdovich.partshop.model.specification.bill.BillSpecificationById;
import by.gvozdovich.partshop.model.specification.bill.BillSpecificationByUserId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link Bill} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class BillService implements Service {
    private static BillService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static BillService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new BillService();
        return instance;
    }

    private BillService() {
        shopDataRepository = BillRepository.getInstance();
    }

    public boolean add(User user, BigDecimal sum, BillInfo billInfo) throws ServiceException {
        Bill bill = new Bill.Builder()
                .withUser(user)
                .withSum(sum)
                .withBillInfo(billInfo)
                .build();
        try {
            shopDataRepository.addDBEntity(bill);
        } catch (RepositoryException e) {
            logger.error("bill " + bill + " add fail");
            throw new ServiceException("bill add fail", e);
        }
        logger.info("bill " + bill + " added");
        return true;
    }

//only read and add!
//    public boolean update(int billId, User user, BigDecimal sum, BillInfo billInfo) throws ServiceException {
//        Bill bill = new Bill.Builder()
//                .withBillId(billId)
//                .withUser(user)
//                .withSum(sum)
//                .withBillInfo(billInfo)
//                .build();
//
//        return realUpdate(bill);
//    }
//    private boolean realUpdate(Bill bill) throws ServiceException {
//        try {
//            shopDataRepository.updateDBEntity(bill);
//        } catch (RepositoryException e) {
//            throw new ServiceException("bill update fail", e);
//        }
//        logger.info("bill " + bill + " updated");
//        return true;
//    }

    public List<Bill> takeAllBill() throws ServiceException {
        DbEntitySpecification specification = new BillAllSpecification();
        return takeBill(specification);
    }

    public List<Bill> takeBillByUserLogin(String login) throws ServiceException {
        User user = UserService.getInstance().takeUserByLogin(login);
        DbEntitySpecification specification = new BillSpecificationByUserId(user.getUserId());
        return takeBill(specification);
    }

    public Bill takeBillById(int id) throws ServiceException {
        DbEntitySpecification specification = new BillSpecificationById(id);
        List<Bill> bills = takeBill(specification);
        if (bills.isEmpty()) {
            logger.error("bill not found. wrong billId :" + id);
            throw new ServiceException("wrong billId :" + id);
        }
        return bills.get(0);
    }

    private List<Bill> takeBill(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<Bill> billList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            billList.add((Bill) dbEntity);
        }
        logger.info("take bill successful. specification :" + specification);
        return billList;
    }
}
