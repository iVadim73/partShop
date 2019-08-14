package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.service.PartService;
import by.gvozdovich.partshop.model.service.UserService;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * class that interacts with the database and accumulates in itself all methods to add/update/remove or query the
 * {@link WishList} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class WishListRepository implements DataRepository {
    private static Logger logger = LogManager.getLogger();
    private static WishListRepository instance;
    private static final String ADD_SQL = "INSERT INTO wish_list (user_id, part_id) VALUES (?, ?)";
    private static final String UPDATE_SQL = "UPDATE wish_list SET user_id=(?), part_id=(?) WHERE wish_list_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM wish_list WHERE wish_list_id=(?)";

    public static WishListRepository getInstance() {
        if(instance == null) {
            instance = new WishListRepository();
        }
        return instance;
    }

    private WishListRepository() {

    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, ((WishList) dbEntity).getUser().getUserId());
            statement.setInt(2, ((WishList) dbEntity).getPart().getPartId());
            statement.execute();
            logger.debug("wishList added :" + dbEntity);

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int autoId = rs.getInt(1);
            return autoId;
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("add wishList", e);
        }
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)
        ) {
            statement.setInt(1, ((WishList) dbEntity).getUser().getUserId());
            statement.setInt(2, ((WishList) dbEntity).getPart().getPartId());
            statement.setInt(3, ((WishList) dbEntity).getWishListId());
            statement.execute();
            logger.debug("wishList updated :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("update", e);
        }
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(REMOVE_SQL)
        ) {
            statement.setInt(1, ((WishList) dbEntity).getWishListId());
            statement.execute();
            logger.debug("wishList removed :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("remove", e);
        }
    }

    @Override
    public List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException {
        List<DbEntity> wishListList = new ArrayList<>();
        try (
            Connection connection = getConnection();
            PreparedStatement statement = specification.specified(connection);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                int partId = resultSet.getInt(ServiceConstant.PART_ID);
                Part part;
                User user;
                try {
                    user = UserService.getInstance().takeUserById(userId);
                    part = PartService.getInstance().takePartById(partId);
                } catch (ServiceException e) {
                    logger.error("ServiceException :" + e);
                    throw new RepositoryException("take user or part fail", e);
                }

                WishList wishList = new WishList.Builder()
                        .withWishListId(resultSet.getInt(ServiceConstant.WISH_LIST_ID))
                        .withUser(user)
                        .withPart(part)
                        .build();

                wishListList.add(wishList);
            }
        } catch (SpecificationException e) {
            logger.error("SpecificationException :" + e);
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("Repository execute fail", e);
        }
        logger.debug("wishList query :" + wishListList);
        return wishListList;
    }
}
