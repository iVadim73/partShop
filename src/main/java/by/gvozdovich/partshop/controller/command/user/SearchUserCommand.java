package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * search User from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class SearchUserCommand implements Command {

    public SearchUserCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show user page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            page = new TagCommand().execute(request);

            int pageCount = (int) request.getAttribute(CommandVarConstant.PAGE_COUNT);
            String data = request.getParameter(CommandVarConstant.PART_OF_USER_LOGIN);
            List<User> users;
            UserValidator validator = new UserValidator();

            if (data.isEmpty()) {
                users = UserService.getInstance().takeAllUser(pageCount);
            } else if (!validator.partLoginValidate(data)) {
                logger.error("wrong data :" + data);
                return goError(request, "wrong data");
            } else {
                users = UserService.getInstance().takeUser(data);
            }

            request.setAttribute(CommandVarConstant.USERS, users);
            page = new TagCommand().execute(request);

            String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);

            switch (type) {
                case CommandVarConstant.ADMIN:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_ADMIN);
                    break;
                case CommandVarConstant.SELLER:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_SELLER);
                    break;
                default:
                    page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
                    break;
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
