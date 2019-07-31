package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.BillRepository;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.UserRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.bill.BillAllSpecification;
import by.gvozdovich.partshop.model.specification.bill.BillSpecificationById;
import by.gvozdovich.partshop.model.specification.bill.BillSpecificationByUserId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillService {
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
            throw new ServiceException("bill add fail", e);
        }
        logger.info("bill " + bill + " added");
        return true;
    }

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
//только добабление и чтение
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
            throw new ServiceException("wrong billId :" + id);
        }
        Bill bill = bills.get(0);
        return bill;
    }

    private List<Bill> takeBill(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<Bill> bills = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take bill fail", e);
        }
        try {
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                User user = UserService.getInstance().takeUserById(userId);

                int billInfoId = resultSet.getInt(ServiceConstant.BILL_INFO_ID);
                BillInfo billInfo = BillInfoService.getInstance().takeBillInfoById(billInfoId);
                LocalDate date = Timestamp.valueOf(resultSet.getString(ServiceConstant.DATE))
                        .toLocalDateTime().toLocalDate();

                Bill bill = new Bill.Builder()
                        .withBillId(resultSet.getInt(ServiceConstant.BILL_ID))
                        .withUser(user)
                        .withSum(resultSet.getBigDecimal(ServiceConstant.SUM))
                        .withBillInfo(billInfo)
                        .withDate(date)
                        .build();

                bills.add(bill);
            }
        } catch (SQLException e) {
            throw new ServiceException("take bill fail", e);
        }
        return bills;
    }


}
