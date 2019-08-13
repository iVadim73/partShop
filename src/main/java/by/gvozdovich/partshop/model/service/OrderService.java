package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.OrderRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.order.OrderAllSpecification;
import by.gvozdovich.partshop.model.specification.order.OrderSpecificationById;
import by.gvozdovich.partshop.model.specification.order.OrderSpecificationByUserId;
import by.gvozdovich.partshop.model.specification.order.OrderSpecificationByUserIdForUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link Order} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class OrderService implements Service {
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

    public boolean update(int orderId, BigDecimal cost, Condition condition, int partCount, boolean isActive) throws ServiceException {
        Order order = takeOrderById(orderId);
        Order.Builder builder = takeBuilder(order);

        Order newOrder = builder
                .withCost(cost)
                .withCondition(condition)
                .withPartCount(partCount)
                .withIsActive(isActive)
                .build();

        return realUpdate(newOrder);
    }

    public boolean update(int orderId, Condition condition, boolean isActive) throws ServiceException {
        Order order = takeOrderById(orderId);
        Order.Builder builder = takeBuilder(order);

        Order newOrder = builder
                .withCondition(condition)
                .withIsActive(isActive)
                .build();

        return realUpdate(newOrder);
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
                .withDateCondition(order.getDateCondition())
                .withBill(order.getBill());
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

    public List<Order> takeOrderByUserLoginForUser(String login) throws ServiceException {
        User user = UserService.getInstance().takeUserByLogin(login);
        DbEntitySpecification specification = new OrderSpecificationByUserIdForUser(user.getUserId());
        return takeOrder(specification);
    }

    public Order takeOrderById(int id) throws ServiceException {
        DbEntitySpecification specification = new OrderSpecificationById(id);
        List<Order> orders = takeOrder(specification);
        if (orders.isEmpty()) {
            throw new ServiceException("wrong orderId :" + id);
        }
        return orders.get(0);
    }

    private List<Order> takeOrder(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<Order> orderList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            orderList.add((Order) dbEntity);
        }
        return orderList;
    }
}
