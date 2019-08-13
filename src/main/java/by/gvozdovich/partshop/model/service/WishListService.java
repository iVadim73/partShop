package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.*;
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
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link WishList} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class WishListService implements Service {
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
        return wishListList.get(0);
    }

    private List<WishList> takeWishList(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<WishList> wishListList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            wishListList.add((WishList) dbEntity);
        }
        return wishListList;
    }
}
