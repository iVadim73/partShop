package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.DbEntity;
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
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link Role} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class RoleService implements Service {
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
        List<Role> roleList = takeRole(specification);
        if (roleList.isEmpty()) {
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
            List<Role> roleList = takeRole(specification);
            if (roleList.isEmpty()) {
                shopDataRepository.updateDBEntity(role);
                logger.info("role " + type + " updated");
            } else {
                logger.warn("role " + type + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("role update fail", e);
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
        return roles.get(0);
    }

    private List<Role> takeRole(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<Role> roleList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            roleList.add((Role) dbEntity);
        }
        return roleList;
    }
}
