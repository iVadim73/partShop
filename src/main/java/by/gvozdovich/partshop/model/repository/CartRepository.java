package by.gvozdovich.partshop.model.repository;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.exception.SpecificationException;
import by.gvozdovich.partshop.model.service.PartService;
import by.gvozdovich.partshop.model.service.UserService;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * class that interacts with the database and accumulates in itself all methods to add/update/remove or query the
 * {@link Cart} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class CartRepository implements DataRepository {
    private static CartRepository instance;
    private static final String ADD_SQL = "INSERT INTO cart (user_id, part_id, count) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE cart SET user_id=(?), part_id=(?), count=(?) WHERE cart_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM cart WHERE cart_id=(?)";

    public static CartRepository getInstance() {
        if(instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    private CartRepository() {

    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ((Cart) dbEntity).getUser().getUserId());
            statement.setInt(2, ((Cart) dbEntity).getPart().getPartId());
            statement.setInt(3, ((Cart) dbEntity).getCount());
            statement.execute();

            rs = statement.getGeneratedKeys();
            rs.next();
            int autoId = rs.getInt(1);
            return autoId;
        } catch (SQLException e) {
            throw new RepositoryException("add cart", e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setInt(1, ((Cart) dbEntity).getUser().getUserId());
            statement.setInt(2, ((Cart) dbEntity).getPart().getPartId());
            statement.setInt(3, ((Cart) dbEntity).getCount());
            statement.setInt(4, ((Cart) dbEntity).getCartId());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("update", e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void removeDBEntity(DbEntity dbEntity) throws RepositoryException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(REMOVE_SQL);
            statement.setInt(1, ((Cart) dbEntity).getCartId());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("remove", e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException {
        ResultSet resultSet = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        List<DbEntity> cartList = new ArrayList<>();
        try {
            statement = specification.specified(connection);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                int partId = resultSet.getInt(ServiceConstant.PART_ID);
                User user;
                Part part;
                try {
                    user = UserService.getInstance().takeUserById(userId);
                    part = PartService.getInstance().takePartById(partId);
                } catch (ServiceException e) {
                    throw new RepositoryException("take user or part fail", e);
                }

                Cart cart = new Cart.Builder()
                        .withCartId(resultSet.getInt(ServiceConstant.CART_ID))
                        .withUser(user)
                        .withPart(part)
                        .withCount(resultSet.getInt(ServiceConstant.COUNT))
                        .build();

                cartList.add(cart);
            }
        } catch (SpecificationException e) {
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
            throw new RepositoryException("Repository execute fail", e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
        return cartList;
    }
}
