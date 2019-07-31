package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.OrderRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.order.OrderAllSpecification;
import by.gvozdovich.partshop.model.specification.order.OrderSpecificationById;
import by.gvozdovich.partshop.model.specification.order.OrderSpecificationByUserId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static OrderService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static OrderService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new OrderService();
        return instance;
    }

    private OrderService() {
        shopDataRepository = OrderRepository.getInstance();
    }

    public boolean add(User user, Part part, BigDecimal cost, Condition condition, int partCount,
                       boolean isActive) throws ServiceException {
        Order order = new Order.Builder()
                .withUser(user)
                .withPart(part)
                .withCost(cost)
                .withCondition(condition)
                .withPartCount(partCount)
                .withIsActive(isActive)
                .build();
        try {
            shopDataRepository.addDBEntity(order);
        } catch (RepositoryException e) {
            throw new ServiceException("order add fail", e);
        }
        logger.info("order " + order + " added");
        return true;
    }

//    public boolean buy(User user, Part part, BigDecimal cost, Condition condition, int partCount,
//                       boolean isActive) throws ServiceException {
//        Order order = new Order.Builder()
//                .withUser(user)
//                .withPart(part)
//                .withCost(cost)
//                .withCondition(condition)
//                .withPartCount(partCount)
//                .withIsActive(isActive)
//                .build();
//        try {
//            ((OrderRepository)shopDataRepository).buy(order);
//        } catch (RepositoryException e) {
//            throw new ServiceException("cart buy fail", e);
//        }
//        logger.info("order " + order + " added");
//        return true;
//    }

    public boolean activateDeactivate(Order order) throws ServiceException {
        boolean isActive = !order.getIsActive();
        Order updatedOrder = takeBuilder(order)
                .withIsActive(isActive)
                .build();
        return realUpdate(updatedOrder);
    }

    public boolean updateCondition(Order order, Condition condition) throws ServiceException {
        Order updatedOrder = takeBuilder(order)
                .withCondition(condition)
                .build();
        return realUpdate(updatedOrder);
    }

    public boolean update(int orderId, User user, Part part, LocalDate orderDate, BigDecimal cost, Condition condition,
                          LocalDate conditionDate, int partCount, boolean isActive) throws ServiceException {
        Order order = new Order.Builder()
                .withOrderId(orderId)
                .withUser(user)
                .withPart(part)
                .withDateOrder(orderDate)
                .withCost(cost)
                .withCondition(condition)
                .withDateCondition(conditionDate)
                .withPartCount(partCount)
                .withIsActive(isActive)
                .build();

        return realUpdate(order);
    }

    private Order.Builder takeBuilder(Order order){
        Order.Builder builder = new Order.Builder();
        builder.withOrderId(order.getOrderId())
                .withUser(order.getUser())
                .withPart(order.getPart())
                .withCost(order.getCost())
                .withCondition(order.getCondition())
                .withPartCount(order.getPartCount())
                .withIsActive(order.getIsActive())
                .withDateOrder(order.getDateOrder())
                .withDateCondition(order.getDateCondition());
        return builder;
    }

    private boolean realUpdate(Order order) throws ServiceException {
        try {
            shopDataRepository.updateDBEntity(order);
        } catch (RepositoryException e) {
            throw new ServiceException("order update fail", e);
        }
        logger.info("order " + order + " updated");
        return true;
    }

    public List<Order> takeAllOrder() throws ServiceException {
        DbEntitySpecification specification = new OrderAllSpecification();
        return takeOrder(specification);
    }

    public List<Order> takeOrderByUserLogin(String login) throws ServiceException {
        User user = UserService.getInstance().takeUserByLogin(login);
        DbEntitySpecification specification = new OrderSpecificationByUserId(user.getUserId());
        return takeOrder(specification);
    }

    public Order takeOrderById(int id) throws ServiceException {
        DbEntitySpecification specification = new OrderSpecificationById(id);
        List<Order> orders = takeOrder(specification);
        if (orders.isEmpty()) {
            throw new ServiceException("wrong orderId :" + id);
        }
        Order order = orders.get(0);
        return order;
    }

    private List<Order> takeOrder(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<Order> orders = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take order fail", e);
        }
        try {
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                User user = UserService.getInstance().takeUserById(userId);

                int partId = resultSet.getInt(ServiceConstant.PART_ID);
                Part part = PartService.getInstance().takePartById(partId);

                int conditionId = resultSet.getInt(ServiceConstant.CONDITION_ID);
                Condition condition = ConditionService.getInstance().takeConditionById(conditionId);

                LocalDate orderDate = Timestamp.valueOf(resultSet.getString(ServiceConstant.ORDER_DATE))
                        .toLocalDateTime().toLocalDate();
                LocalDate conditionDate = Timestamp.valueOf(resultSet.getString(ServiceConstant.CONDITION_DATE))
                        .toLocalDateTime().toLocalDate();

                Order order = new Order.Builder()
                        .withOrderId(resultSet.getInt(ServiceConstant.ORDER_ID))
                        .withUser(user)
                        .withPart(part)
                        .withDateOrder(orderDate)
                        .withCost(resultSet.getBigDecimal(ServiceConstant.COST))
                        .withCondition(condition)
                        .withDateCondition(conditionDate)
                        .withPartCount(resultSet.getInt(ServiceConstant.PART_COUNT))
                        .withIsActive(resultSet.getBoolean(ServiceConstant.IS_ACTIVE))
                        .build();

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new ServiceException("take order fail", e);
        }
        return orders;
    }
}
