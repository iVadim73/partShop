package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Condition;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.ConditionRepository;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.condition.ConditionAllSpecification;
import by.gvozdovich.partshop.model.specification.condition.ConditionSpecificationById;
import by.gvozdovich.partshop.model.specification.condition.ConditionSpecificationByName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConditionService {
    private static ConditionService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static ConditionService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new ConditionService();
        return instance;
    }

    private ConditionService() {
        shopDataRepository = ConditionRepository.getInstance();
    }

    public boolean add(String name, String info) throws ServiceException {
        DbEntitySpecification specification = new ConditionSpecificationByName(name);
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.next()) {
                Condition condition = new Condition.Builder()
                        .withName(name)
                        .withInfo(info)
                        .build();
                try {
                    shopDataRepository.addDBEntity(condition);
                } catch (RepositoryException e) {
                    throw new ServiceException("condition add fail", e);
                }
                logger.info("condition " + name + " added");
            } else {
                logger.warn("condition " + name + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("condition add fail", e);
        } catch (SQLException e) {
            throw new ServiceException("condition add SQL fail", e);
        }
        return true;
    }

    public boolean update(int conditionId, String name, String info) throws ServiceException {
        Condition condition = new Condition.Builder()
                .withConditionId(conditionId)
                .withName(name)
                .withInfo(info)
                .build();

        return realUpdate(condition);
    }

    private boolean realUpdate(Condition condition) throws ServiceException {
        String name = condition.getName();
        DbEntitySpecification specification = new ConditionSpecificationByName(name);
        try {
            ResultSet resultSet = shopDataRepository.query(specification);
            if (!resultSet.isBeforeFirst()) {
                shopDataRepository.updateDBEntity(condition);
                logger.info("condition " + name + " updated");
            } else {
                logger.warn("condition " + name + " already added");
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException("condition update fail", e);
        } catch (SQLException e) {
            throw new ServiceException("condition update SQL fail", e);
        }
        return true;
    }

    public List<Condition> takeAllCondition() throws ServiceException {
        DbEntitySpecification specification = new ConditionAllSpecification();
        return takeCondition(specification);
    }

    public Condition takeConditionById(int id) throws ServiceException {
        DbEntitySpecification specification = new ConditionSpecificationById(id);
        List<Condition> conditions = takeCondition(specification);
        if (conditions.isEmpty()) {
            throw new ServiceException("wrong conditionId :" + id);
        }
        Condition condition = conditions.get(0);
        return condition;
    }

    private List<Condition> takeCondition(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<Condition> conditions = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take condition fail", e);
        }
        try {
            while (resultSet.next()) {
                Condition condition = new Condition.Builder()
                        .withConditionId(resultSet.getInt(ServiceConstant.CONDITION_ID))
                        .withName(resultSet.getString(ServiceConstant.NAME))
                        .withInfo(resultSet.getString(ServiceConstant.INFO))
                        .build();

                conditions.add(condition);
            }
        } catch (SQLException e) {
            throw new ServiceException("take condition fail", e);
        }
        return conditions;
    }
}
