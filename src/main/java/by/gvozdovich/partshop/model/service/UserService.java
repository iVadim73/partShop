package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.entity.Role;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.UserRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link User} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UserService implements Service {
    private static UserService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static UserService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new UserService();
        return instance;
    }

    private UserService() {
        shopDataRepository = UserRepository.getInstance();
    }

    public boolean registrate(String login, String password, String email, long phone, String name, Role role) throws ServiceException {
        DbEntitySpecification specification = new UserSpecificationByLoginOrEmail(login, email);
        List<User> userList = takeUser(specification);
        if (userList.isEmpty()) {
            User.Builder builder = new User.Builder();
            User user = builder.withLogin(login)
                    .withNewPassword(password)
                    .withEmail(email)
                    .withPhone(phone)
                    .withName(name)
                    .withType(role)
                    .build();
            try {
                shopDataRepository.addDBEntity(user);
            } catch (RepositoryException e) {
                throw new ServiceException("registration fail", e);
            }
            logger.info("user " + login + " registered");
        } else {
            return false;
        }
        return true;
    }

    public boolean signin(String login, String password) throws ServiceException {
        DbEntitySpecification specification = new UserSpecificationByLoginAndPassword(login, password);
        List<User> userList = takeUser(specification);
        if(!userList.isEmpty()) {
            logger.info("user " + login + " signed in");
            User user = takeUserByLogin(login);
            return user.getIsActive();
        }
        return false;
    }

    public boolean activateDeactivate(User user) throws ServiceException {
        boolean isActive = !user.getIsActive();
        User updatedUser = takeBuilder(user)
                .withIsActive(isActive)
                .build();
        return realUpdate(updatedUser);
    }

    public boolean updatePassword(User user, String password) throws ServiceException {
        User updatedUser = takeBuilder(user)
                .withNewPassword(password)
                .build();
        return realUpdate(updatedUser);
    }

    public boolean updatePhone(User user, long phone) throws ServiceException {
        User updatedUser = takeBuilder(user)
                .withPhone(phone)
                .build();
        return realUpdate(updatedUser);
    }

    public boolean updateName(User user, String name) throws ServiceException {
        User updatedUser = takeBuilder(user)
                .withName(name)
                .build();
        return realUpdate(updatedUser);
    }

    public boolean update(int userId, long phone, String name, double discount, int star, Role role, String comment, boolean isActive) throws ServiceException {
        User user = takeUserById(userId);
        User newUser = takeBuilder(user)
                .withPhone(phone)
                .withName(name)
                .withDiscount(discount)
                .withStar(star)
                .withType(role)
                .withComment(comment)
                .withIsActive(isActive)
                .build();
        return realUpdate(newUser);
    }

    public boolean update(int userId, long phone, String name, double discount, int star, String comment) throws ServiceException {
        User user = takeUserById(userId);
        User newUser = takeBuilder(user)
                .withPhone(phone)
                .withName(name)
                .withDiscount(discount)
                .withStar(star)
                .withComment(comment)
                .build();
        return realUpdate(newUser);
    }

//    public boolean updateDiscount(User user, double discount) throws ServiceException {
//        User updatedUser = takeBuilder(user)
//                .withDiscount(discount)
//                .build();
//        return realUpdate(updatedUser);
//    }
//
//    public boolean updateStar(User user, int star) throws ServiceException {
//        User updatedUser = takeBuilder(user)
//                .withStar(star)
//                .build();
//        return realUpdate(updatedUser);
//    }
//
//    public boolean updateComment(User user, String comment) throws ServiceException {
//        User updatedUser = takeBuilder(user)
//                .withComment(comment)
//                .build();
//        return realUpdate(updatedUser);
//    }
//
//
//    public boolean updateRole(User user, Role role) throws ServiceException {
//        User updatedUser = takeBuilder(user)
//                .withType(role)
//                .build();
//        return realUpdate(updatedUser);
//    }

    private User.Builder takeBuilder(User user){
        User.Builder builder = new User.Builder();
        builder.withUserId(user.getUserId())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withEmail(user.getEmail())
                .withPhone(user.getPhone())
                .withName(user.getName())
                .withRegistrationDate(user.getRegistrationDate())
                .withDiscount(user.getDiscount())
                .withStar(user.getStar())
                .withComment(user.getComment())
                .withBill(user.getBill())
                .withType(user.getRole())
                .withIsActive(user.getIsActive());
        return builder;
    }

    private boolean realUpdate(User user) throws ServiceException {
        String login = user.getLogin();
        String email = user.getEmail();
        DbEntitySpecification specification = new UserSpecificationByLoginOrEmail(login, email);
        try {
            List<User> userList = takeUser(specification);
            if (userList.isEmpty()) {
                shopDataRepository.updateDBEntity(user);
                logger.info("user " + login + " updated");
            } else {
                int userId = user.getUserId();
                for (User currentUser: userList) {
                    int currentUserId = currentUser.getUserId();
                    if (userId == currentUserId) {
                        shopDataRepository.updateDBEntity(user);
                        logger.info("user " + login + " updated");
                        return true;
                    }
                }
                logger.warn("user " + user + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("update user fail", e);
        }
        return true;
    }

    public List<User> takeAllUser() throws ServiceException {
        DbEntitySpecification specification = new UserAllSpecification();
        return takeUser(specification);
    }

    public List<User> takeAllUserForSeller() throws ServiceException {
        DbEntitySpecification specification = new UserAllForSellerSpecification();
        return takeUser(specification);
    }

    public User takeUserById(int id) throws ServiceException {
        DbEntitySpecification specification = new UserSpecificationById(id);
        List<User> users = takeUser(specification);
        if (users.isEmpty()) {
            throw new ServiceException("wrong userId :" + id);
        }
        return users.get(0);
    }

    public User takeUserByLogin(String login) throws ServiceException {
        DbEntitySpecification specification = new UserSpecificationByLogin(login);
        List<User> users = takeUser(specification);
        if (users.isEmpty()) {
            throw new ServiceException("wrong login :" + login);
        }
        return users.get(0);
    }

    public List<User> takeUser(String data) throws ServiceException {
        DbEntitySpecification specification = new UserSpecificationLikeLogin(data);
        return takeUser(specification);
    }

    private List<User> takeUser(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<User> userList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            userList.add((User) dbEntity);
        }
        return userList;
    }
}
