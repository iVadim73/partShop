package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.entity.WishList;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.WishListRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.wishlist.WishListAllSpecification;
import by.gvozdovich.partshop.model.specification.wishlist.WishListSpecificationById;
import by.gvozdovich.partshop.model.specification.wishlist.WishListSpecificationByUserAndPart;
import by.gvozdovich.partshop.model.specification.wishlist.WishListSpecificationByUserId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishListService {
    private static WishListService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static WishListService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new WishListService();
        return instance;
    }

    private WishListService() {
        shopDataRepository = WishListRepository.getInstance();
    }

    public boolean add(User user, Part part) throws ServiceException {
        DbEntitySpecification specification = new WishListSpecificationByUserAndPart(user.getUserId(), part.getPartId());
        List<WishList> wishListList = takeWishList(specification);
        if (!wishListList.isEmpty()) {
            return false;
        }
        WishList wishList = new WishList.Builder()
                .withUser(user)
                .withPart(part)
                .build();
        try {
            shopDataRepository.addDBEntity(wishList);
        } catch (RepositoryException e) {
            throw new ServiceException("wishList add fail", e);
        }
        logger.info("wishList " + wishList + " added");
        return true;
    }

    public boolean delete(WishList wishList) throws ServiceException {
        try {
            shopDataRepository.removeDBEntity(wishList);
        } catch (RepositoryException e) {
            throw new ServiceException("wishList remove fail", e);
        }
        return true;
    }

    public boolean update(int cartId, User user, Part part) throws ServiceException {
        WishList wishList = new WishList.Builder()
                .withWishListId(cartId)
                .withUser(user)
                .withPart(part)
                .build();

        return realUpdate(wishList);
    }

    private boolean realUpdate(WishList wishList) throws ServiceException {
        try {
            shopDataRepository.updateDBEntity(wishList);
        } catch (RepositoryException e) {
            throw new ServiceException("wishList update fail", e);
        }
        logger.info("wishList " + wishList + " updated");
        return true;
    }

    public List<WishList> takeAllWishList() throws ServiceException {
        DbEntitySpecification specification = new WishListAllSpecification();
        return takeWishList(specification);
    }

    public List<WishList> takeWishListByUserLogin(String login) throws ServiceException {
        User user = UserService.getInstance().takeUserByLogin(login);
        DbEntitySpecification specification = new WishListSpecificationByUserId(user.getUserId());
        return takeWishList(specification);
    }

    public WishList takeWishListById(int id) throws ServiceException {
        DbEntitySpecification specification = new WishListSpecificationById(id);
        List<WishList> wishListList = takeWishList(specification);
        if (wishListList.isEmpty()) {
            throw new ServiceException("wrong wishListId :" + id);
        }
        WishList wishList = wishListList.get(0);
        return wishList;
    }

    private List<WishList> takeWishList(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<WishList> wishListList = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take wishList fail", e);
        }
        try {
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                User user = UserService.getInstance().takeUserById(userId);

                int partId = resultSet.getInt(ServiceConstant.PART_ID);
                Part part = PartService.getInstance().takePartById(partId);

                WishList wishList = new WishList.Builder()
                        .withWishListId(resultSet.getInt(ServiceConstant.WISH_LIST_ID))
                        .withUser(user)
                        .withPart(part)
                        .build();

                wishListList.add(wishList);
            }
        } catch (SQLException e) {
            throw new ServiceException("take wishList fail", e);
        }
        return wishListList;
    }
}
