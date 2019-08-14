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

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * class that interacts with the database and accumulates in itself all methods to add/update/remove or query the
 * {@link Feedback} of the application
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class FeedbackRepository implements DataRepository {
    private static Logger logger = LogManager.getLogger();
    private static FeedbackRepository instance;
    private static final String ADD_SQL = "INSERT INTO feedback (user_id, part_id, comment, star) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE feedback SET user_id=(?), part_id=(?), comment=(?), star=(?) WHERE feedback_id=(?)";
    private static final String REMOVE_SQL = "DELETE FROM feedback WHERE feedback_id=(?)";

    public static FeedbackRepository getInstance() {
        if(instance == null) {
            instance = new FeedbackRepository();
        }
        return instance;
    }

    private FeedbackRepository() {

    }

    @Override
    public int addDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_SQL, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, ((Feedback) dbEntity).getUser().getUserId());
            statement.setInt(2, ((Feedback) dbEntity).getPart().getPartId());
            statement.setString(3, ((Feedback) dbEntity).getComment());
            statement.setInt(4, ((Feedback) dbEntity).getStar());
            statement.execute();
            logger.debug("feedback added :" + dbEntity);

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int autoId = rs.getInt(1);
            return autoId;
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("add feedback", e);
        }
    }

    @Override
    public void updateDBEntity(DbEntity dbEntity) throws RepositoryException {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)
        ) {
            statement.setInt(1, ((Feedback) dbEntity).getUser().getUserId());
            statement.setInt(2, ((Feedback) dbEntity).getPart().getPartId());
            statement.setString(3, ((Feedback) dbEntity).getComment());
            statement.setInt(4, ((Feedback) dbEntity).getStar());
            statement.setInt(5, ((Feedback) dbEntity).getFeedbackId());
            statement.execute();
            logger.debug("feedback updated :" + dbEntity);
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
            statement.setInt(1, ((Feedback) dbEntity).getFeedbackId());
            statement.execute();
            logger.debug("feedback removed :" + dbEntity);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("remove", e);
        }
    }

    @Override
    public List<DbEntity> query(DbEntitySpecification specification) throws RepositoryException {
        List<DbEntity> feedbackList = new ArrayList<>();
        try (
            Connection connection = getConnection();
            PreparedStatement statement = specification.specified(connection);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int userId = resultSet.getInt(ServiceConstant.USER_ID);
                int partId = resultSet.getInt(ServiceConstant.PART_ID);
                User user;
                Part part;
                try {
                    user = UserService.getInstance().takeUserById(userId);
                    part = PartService.getInstance().takePartById(partId);
                } catch (ServiceException e) {
                    logger.error("ServiceException :" + e);
                    throw new RepositoryException("take user or part bill info fail", e);
                }

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

                feedbackList.add(feedback);
            }
        } catch (SpecificationException e) {
            logger.error("SpecificationException :" + e);
            throw new RepositoryException("Repository statement fail", e);
        } catch (SQLException e) {
            logger.error("SQLException :" + e);
            throw new RepositoryException("Repository execute fail", e);
        }
        logger.debug("feedback query :" + feedbackList);
        return feedbackList;
    }
}
