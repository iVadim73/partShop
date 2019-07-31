package by.gvozdovich.partshop.model.logic;

import by.gvozdovich.partshop.model.ServiceConstant;
import by.gvozdovich.partshop.model.entity.Feedback;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.RepositoryException;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.repository.DataRepository;
import by.gvozdovich.partshop.model.repository.FeedbackRepository;
import by.gvozdovich.partshop.model.specification.DbEntitySpecification;
import by.gvozdovich.partshop.model.specification.feedback.FeedbackAllSpecification;
import by.gvozdovich.partshop.model.specification.feedback.FeedbackSpecificationById;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService {
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

    public boolean add(User user, Part part, LocalDate date, String comment, int star) throws ServiceException {
        Feedback feedback = new Feedback.Builder()
                .withUser(user)
                .withPart(part)
                .withDate(date)
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

    public Feedback takeFeedbackById(int id) throws ServiceException {
        DbEntitySpecification specification = new FeedbackSpecificationById(id);
        List<Feedback> feedbacks = takeFeedback(specification);
        if (feedbacks.isEmpty()) {
            throw new ServiceException("wrong feedbackId :" + id);
        }
        Feedback feedback = feedbacks.get(0);
        return feedback;
    }

    private List<Feedback> takeFeedback(DbEntitySpecification specification) throws ServiceException {
        ResultSet resultSet;
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            resultSet = shopDataRepository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("take feedback fail", e);
        }
        try {
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                User user = UserService.getInstance().takeUserById(userId);

                int partId = resultSet.getInt(ServiceConstant.PART_ID);
                Part part = PartService.getInstance().takePartById(partId);

                LocalDate date = Timestamp.valueOf(resultSet.getString(ServiceConstant.DATE))
                        .toLocalDateTime().toLocalDate();

                Feedback feedback = new Feedback.Builder()
                        .withFeedbackId(resultSet.getInt(ServiceConstant.FEEDBACK_ID))
                        .withUser(user)
                        .withPart(part)
                        .withDate(date)
                        .withComment(resultSet.getString(ServiceConstant.COMMENT))
                        .withStar(resultSet.getInt(ServiceConstant.STAR))
                        .build();

                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            throw new ServiceException("take feedback fail", e);
        }
        return feedbacks;
    }
}
