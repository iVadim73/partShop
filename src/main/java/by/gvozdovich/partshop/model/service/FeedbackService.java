package by.gvozdovich.partshop.model.service;

import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.FeedbackRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.feedback.FeedbackAllSpecification;
import by.gvozdovich.partshop.model.specification.feedback.FeedbackSpecificationById;
import by.gvozdovich.partshop.model.specification.feedback.FeedbackSpecificationByPartId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * encapsulates {@link Feedback} logic to provide needed data to command layer
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class FeedbackService implements Service {
    private static FeedbackService instance;
    private static Logger logger = LogManager.getLogger();
    private DataRepository shopDataRepository;

    public static FeedbackService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new FeedbackService();
        return instance;
    }

    private FeedbackService() {
        shopDataRepository = FeedbackRepository.getInstance();
    }

    public boolean add(User user, Part part, String comment, int star) throws ServiceException {
        Feedback feedback = new Feedback.Builder()
                .withUser(user)
                .withPart(part)
                .withComment(comment)
                .withStar(star)
                .build();
        try {
            shopDataRepository.addDBEntity(feedback);
        } catch (RepositoryException e) {
            throw new ServiceException("feedback add fail", e);
        }
        logger.info("feedback " + feedback + " added");
        return true;
    }

    public boolean delete(Feedback feedback) throws ServiceException {
        try {
            shopDataRepository.removeDBEntity(feedback);
        } catch (RepositoryException e) {
            throw new ServiceException("feedback remove fail", e);
        }
        return true;
    }

    public boolean update(int feedbackId, User user, Part part, LocalDate date, String comment, int star) throws ServiceException {
        Feedback feedback = new Feedback.Builder()
                .withFeedbackId(feedbackId)
                .withUser(user)
                .withPart(part)
                .withDate(date)
                .withComment(comment)
                .withStar(star)
                .build();

        return realUpdate(feedback);
    }

    private boolean realUpdate(Feedback feedback) throws ServiceException {
        try {
            shopDataRepository.updateDBEntity(feedback);
        } catch (RepositoryException e) {
            throw new ServiceException("feedback update fail", e);
        }
        logger.info("feedback " + feedback + " updated");
        return true;
    }

    public List<Feedback> takeAllFeedback() throws ServiceException {
        DbEntitySpecification specification = new FeedbackAllSpecification();
        return takeFeedback(specification);
    }

    public Feedback takeFeedbackById(int feedbackId) throws ServiceException {
        DbEntitySpecification specification = new FeedbackSpecificationById(feedbackId);
        List<Feedback> feedbackList = takeFeedback(specification);
        if (feedbackList.isEmpty()) {
            throw new ServiceException("wrong feedbackId :" + feedbackId);
        }
        return feedbackList.get(0);
    }

    public List<Feedback> takeAllFeedbackByPartId(int partId) throws ServiceException {
        DbEntitySpecification specification = new FeedbackSpecificationByPartId(partId);
        return takeFeedback(specification);
    }

    private List<Feedback> takeFeedback(DbEntitySpecification specification) throws ServiceException {
        List<DbEntity> dbEntityList = takeDbEntityList(shopDataRepository, specification);
        List<Feedback> feedbackList = new ArrayList<>();
        for (DbEntity dbEntity: dbEntityList) {
            feedbackList.add((Feedback) dbEntity);
        }
        return feedbackList;
    }
}
