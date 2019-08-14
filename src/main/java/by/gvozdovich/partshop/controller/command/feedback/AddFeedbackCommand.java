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
import java.io.UnsupportedEncodingException;

/**
 * add Feedback to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddFeedbackCommand implements Command {

    public AddFeedbackCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show part page
     */
    @Override
    public Router execute(HttpServletRequest request) {
    Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String comment = null;
            try {
                comment = new String(request.getParameter(CommandVarConstant.COMMENT).getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e);
            }
            String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);
            User user = UserService.getInstance().takeUserByLogin(currentLogin);
            int partId = Integer.parseInt(request.getParameter(CommandVarConstant.PART_ID));
            Part part = PartService.getInstance().takePartById(partId);
            String strStar = request.getParameter(CommandVarConstant.STAR);

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
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
