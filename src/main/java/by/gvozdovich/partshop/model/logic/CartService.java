package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.CartRepository;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.OrderRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.cart.CartAllSpecification;
import by.gvozdovich.partshop.model.specification.cart.CartSpecificationById;
import by.gvozdovich.partshop.model.specification.cart.CartSpecificationByPartIdAndUserId;
import by.gvozdovich.partshop.model.specification.cart.CartSpecificationByUserId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private static CartService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static CartService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new CartService();
        return instance;
    }

    private CartService() {
        shopDataRepository = CartRepository.getInstance();
    }

    public boolean add(User user, Part part, int count) throws ServiceException {
        Cart cart = new Cart.Builder()
                .withUser(user)
                .withPart(part)
                .withCount(count)
                .build();
        try {
            shopDataRepository.addDBEntity(cart);
        } catch (RepositoryException e) {
            throw new ServiceException("cart add fail", e);
        }
        logger.info("cart " + cart + " added");
        return true;
    }

    public boolean buy(int cartId, User user, Part part, int count) throws ServiceException { // TODO: 2019-07-29 тут ли???
        Cart cart = new Cart.Builder()
                .withCartId(cartId)
                .withUser(user)
                .withPart(part)
                .withCount(count)
                .build();
        try {
            OrderRepository orderRepository = OrderRepository.getInstance(); // TODO: 2019-07-29 можно ли??? 
            orderRepository.buy(cart);
        } catch (RepositoryException e) {
            throw new ServiceException("cart buy fail", e);
        }
        logger.info("cart " + cart + " bought");
        return true;
    }

    public boolean delete(Cart cart) throws ServiceException {
        try {
            shopDataRepository.removeDBEntity(cart);
        } catch (RepositoryException e) {
            throw new ServiceException("cart remove fail", e);
        }
        return true;
    }

    public boolean update(int cartId, User user, Part part, int count) throws ServiceException {
        Cart cart = new Cart.Builder()
                .withCartId(cartId)
                .withUser(user)
                .withPart(part)
                .withCount(count)
                .build();

        return realUpdate(cart);
    }

    private boolean realUpdate(Cart cart) throws ServiceException {
        try {
            shopDataRepository.updateDBEntity(cart);
        } catch (RepositoryException e) {
            throw new ServiceException("cart update fail", e);
        }
        logger.info("cart " + cart + " updated");
        return true;
    }

    public List<Cart> takeAllCart() throws ServiceException {
        DbEntitySpecification specification = new CartAllSpecification();
        return takeCart(specification);
    }

    public List<Cart> takeCartByUserLogin(String login) throws ServiceException {
        User user = UserService.getInstance().takeUserByLogin(login);
        DbEntitySpecification specification = new CartSpecificationByUserId(user.getUserId());
        return takeCart(specification);
    }

    public Cart takeCartById(int cartId) throws ServiceException {
        DbEntitySpecification specification = new CartSpecificationById(cartId);
        List<Cart> carts = takeCart(specification);
        if (carts.isEmpty()) {
            throw new ServiceException("wrong cartId :" + cartId);
        }
        Cart cart = carts.get(0);
        return cart;
    }

    public Cart takeCartByPartIdAndUserId(int partId, int userId) throws ServiceException {
        DbEntitySpecification specification = new CartSpecificationByPartIdAndUserId(partId, userId);
        List<Cart> carts = takeCart(specification);
        if (carts.isEmpty()) {
            throw new ServiceException("wrong partId :" + partId + " or userId :" + userId);
        }
        Cart cart = carts.get(0);
        return cart;
    }

    private List<Cart> takeCart(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<Cart> carts = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take cart fail", e);
        }
        try {
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                User user = UserService.getInstance().takeUserById(userId);

                int partId = resultSet.getInt(ServiceConstant.PART_ID);
                Part part = PartService.getInstance().takePartById(partId);

                Cart cart = new Cart.Builder()
                        .withCartId(resultSet.getInt(ServiceConstant.CART_ID))
                        .withUser(user)
                        .withPart(part)
                        .withCount(resultSet.getInt(ServiceConstant.COUNT))
                        .build();

                carts.add(cart);
            }
        } catch (SQLException e) {
            throw new ServiceException("take cart fail", e);
        }
        return carts;
    }
}
