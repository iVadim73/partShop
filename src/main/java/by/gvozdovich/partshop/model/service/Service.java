package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.DbEntity;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;

import java.util.List;

/**
 * encapsulates functionality interaction with repository
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public interface Service {
    default List<DbEntity> takeDbEntityList(DataRepository shopDataRepository, DbEntitySpecification specification) throws ServiceException {
        try {
            return shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take DbEntity fail", e);
        }
    }
}
