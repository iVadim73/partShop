package by.gvozdovich.partshop.controller.command.feedback;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.FeedbackValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.FeedbackService;
import by.gvozdovich.partshop.model.service.PartService;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * add Feedback to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddFeedbackCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    public AddFeedbackCommand() {
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
            String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);
            User user = UserService.getInstance().takeUserByLogin(currentLogin);
            int partId = Integer.parseInt(request.getParameter(CommandVarConstant.PART_ID));
            Part part = PartService.getInstance().takePartById(partId);
            String strStar = request.getParameter(CommandVarConstant.STAR);
            String comment = request.getParameter(CommandVarConstant.COMMENT);

            FeedbackValidator validator = new FeedbackValidator();
            if (!validator.feedbackValidate(strStar, comment)) {
                request.setAttribute(CommandVarConstant.CONDITION, "wrong data");
            } else {
                int star = Integer.parseInt(strStar);
                FeedbackService.getInstance().add(user, part, comment, star);
                request.setAttribute(CommandVarConstant.CONDITION, "feedback added");
            }

            page.setRouterType(Router.RouterType.REDIRECT);
            String pageToRedirect;
            String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);

            switch (userType) {
                case CommandVarConstant.BUYER:
                    pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWPART_FOR_USER + "?partId=" + partId;
                    page.setPage(pageToRedirect);
                    break;
                case CommandVarConstant.SELLER:
                case CommandVarConstant.ADMIN:
                    pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWPART + "?partId=" + partId;
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
