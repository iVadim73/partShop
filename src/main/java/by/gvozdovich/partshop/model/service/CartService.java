package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.CartRepository;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.OrderRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.cart.CartSpecificationById;
import by.gvozdovich.partshop.model.specification.cart.CartSpecificationByPartIdAndUserId;
import by.gvozdovich.partshop.model.specification.cart.CartSpecificationByUserId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link Cart} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class CartService implements Service {
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

    public boolean buy(int cartId, User demoUser, Part part, int count) throws ServiceException {
        int userId = demoUser.getUserId();
        User user = UserService.getInstance().takeUserById(userId);
        Cart cart = new Cart.Builder()
                .withCartId(cartId)
                .withUser(user)
                .withPart(part)
                .withCount(count)
                .build();
        try {
            OrderRepository orderRepository = OrderRepository.getInstance();
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

//    public List<Cart> takeAllCart() throws ServiceException {
//        DbEntitySpecification specification = new CartAllSpecification();
//        return takeCart(specification);
//    }

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
        return carts.get(0);
    }

    public Cart takeCartByPartIdAndUserId(int partId, int userId) throws ServiceException {
        DbEntitySpecification specification = new CartSpecificationByPartIdAndUserId(partId, userId);
        List<Cart> carts = takeCart(specification);
        if (carts.isEmpty()) {
            throw new ServiceException("wrong partId :" + partId + " or userId :" + userId);
        }
        return carts.get(0);
    }

    private List<Cart> takeCart(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<Cart> cartList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            cartList.add((Cart) dbEntity);
        }
        return cartList;
    }
}
