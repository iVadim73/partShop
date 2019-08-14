package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.cart.AddCartCommand;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.command.wishlist.AddWishListCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * sign in User
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class SignInCommand implements Command {

    public SignInCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to signin page if data is wrong
     * forward to index page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String login = request.getParameter(CommandVarConstant.LOGIN);
            String password = request.getParameter(CommandVarConstant.PASSWORD);
            String hashPassword = User.hashPassword(password);
            request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_SIGNIN);

            UserValidator validator = new UserValidator();
            if (!validator.signinValidate(login, password)) {
                logger.error("wrong data :" + login + " " + password);
                request.getSession().setAttribute(CommandVarConstant.LOGIN, login);
                request.getSession().setAttribute(CommandVarConstant.PASSWORD, password);
                request.setAttribute(CommandVarConstant.CONDITION, "wrong data");

                page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            } else if (UserService.getInstance().signin(login, hashPassword)) {
                request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
                request.getSession().removeAttribute(CommandVarConstant.LOGIN);
                request.getSession().removeAttribute(CommandVarConstant.PASSWORD);

                request.getSession().setAttribute(CommandVarConstant.CURRENT_LOGIN, login);
                User user = UserService.getInstance().takeUserByLogin(login);
                String userType = user.getRole().getType();
                request.getSession().setAttribute(CommandVarConstant.USER_TYPE, userType);
                int userId = user.getUserId();
                request.getSession().setAttribute(CommandVarConstant.USER_ID, userId);

                if (CommandPathConstant.PATH_PAGE_SHOWALLPART.equals(request.getSession().getAttribute(CommandVarConstant.LAST_PATH))) {
                    String action = (String) request.getSession().getAttribute(CommandVarConstant.ACTION);
                    request.getSession().removeAttribute(CommandVarConstant.ACTION);

                    request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_SIGNIN);

                    if (action.equals(CommandVarConstant.ADD_WISH_LIST)) {
                        page = new AddWishListCommand().execute(request);
                    } else if (action.equals(CommandVarConstant.ADD_CART)) {
                        page = new AddCartCommand().execute(request);
                    }
                } else {
                    page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
                }
            } else {
                request.getSession().setAttribute(CommandVarConstant.LOGIN, login);
                request.getSession().setAttribute(CommandVarConstant.PASSWORD, password);
                request.setAttribute(CommandVarConstant.CONDITION, "wrong login or password");
                page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
