package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * take and show all User from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ShowAllUserCommand implements Command {

    public ShowAllUserCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to page with result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
            List<User> users = null;

            page = new TagCommand().execute(request);

            switch (type) {
                case CommandVarConstant.ADMIN:
                    users = UserService.getInstance().takeAllUser();
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_ADMIN);
                    break;
                case CommandVarConstant.SELLER:
                    users = UserService.getInstance().takeAllUserForSeller();
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_SELLER);
                    break;
                default:
                    page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
                    break;
            }

            request.setAttribute(CommandVarConstant.USERS, users);
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
