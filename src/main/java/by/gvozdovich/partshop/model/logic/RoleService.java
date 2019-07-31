package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Role;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.RoleRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.role.RoleAllSpecification;
import by.gvozdovich.partshop.model.specification.role.RoleSpecificationById;
import by.gvozdovich.partshop.model.specification.role.RoleSpecificationByType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleService {
    private static RoleService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static RoleService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new RoleService();
        return instance;
    }

    private RoleService() {
        shopDataRepository = RoleRepository.getInstance();
    }

    public boolean add(String type) throws ServiceException {
        DbEntitySpecification specification = new RoleSpecificationByType(type);
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.next()) {
                Role role = new Role.Builder()
                        .withType(type)
                        .build();
                try {
                    shopDataRepository.addDBEntity(role);
                } catch (RepositoryException e) {
                    throw new ServiceException("role add fail", e);
                }
                logger.info("role " + type + " added");
            } else {
                logger.warn("role " + type + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("role add fail", e);
        } catch (SQLException e) {
            throw new ServiceException("role add SQL fail", e);
        }
        return true;
    }

    public boolean update(int roleId, String type) throws ServiceException {
        Role role = new Role.Builder()
                .withRoleId(roleId)
                .withType(type)
                .build();

        return realUpdate(role);
    }

    private boolean realUpdate(Role role) throws ServiceException {
        String type = role.getType();
        DbEntitySpecification specification = new RoleSpecificationByType(type);
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.isBeforeFirst()) {
                shopDataRepository.updateDBEntity(role);
                logger.info("role " + type + " updated");
            } else {
                logger.warn("role " + type + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("role update fail", e);
        } catch (SQLException e) {
            throw new ServiceException("role update SQL fail", e);
        }
        return true;
    }

    public List<Role> takeAllRole() throws ServiceException {
        DbEntitySpecification specification = new RoleAllSpecification();
        return takeRole(specification);
    }

    public Role takeRoleById(int id) throws ServiceException {
        DbEntitySpecification specification = new RoleSpecificationById(id);
        List<Role> roles = takeRole(specification);
        if (roles.isEmpty()) {
            throw new ServiceException("wrong roleId :" + id);
        }
        Role role = roles.get(0);
        return role;
    }

    private List<Role> takeRole(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<Role> roles = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take role fail", e);
        }
        try {
            while (resultSet.next()) {
                Role role = new Role.Builder()
                        .withRoleId(resultSet.getInt(ServiceConstant.ROLE_ID))
                        .withType(resultSet.getString(ServiceConstant.TYPE))
                        .build();

                roles.add(role);
            }
        } catch (SQLException e) {
            throw new ServiceException("take role fail", e);
        }
        return roles;
    }
}
