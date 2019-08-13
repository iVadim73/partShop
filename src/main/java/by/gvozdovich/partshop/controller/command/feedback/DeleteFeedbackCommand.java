package by.gvozdovich.partshop.controller.command.feedback;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Feedback;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.FeedbackService;
import javax.servlet.http.HttpServletRequest;

/**
 * delete Feedback from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class DeleteFeedbackCommand implements Command {

    public DeleteFeedbackCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show part page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            int feedbackId = Integer.parseInt(request.getParameter(CommandVarConstant.FEEDBACK_ID));
            Feedback feedback = FeedbackService.getInstance().takeFeedbackById(feedbackId);
            FeedbackService.getInstance().delete(feedback);
            request.setAttribute(CommandVarConstant.CONDITION, "feedback deleted successfully");

            int partId = feedback.getPart().getPartId();
            page.setRouterType(Router.RouterType.REDIRECT);
            String pageToRedirect;
            String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);

            switch (userType) {
                case CommandVarConstant.SELLER:
                case CommandVarConstant.ADMIN:
                    pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWPART + "?partId=" + partId;
                    page.setPage(pageToRedirect);
                    break;
                case CommandVarConstant.BUYER:
                    pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWPART_FOR_USER + "?partId=" + partId;
                    page.setPage(pageToRedirect);
                    break;
                default:
                    page = goError(request, "access fail");
                    break;
            }
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
