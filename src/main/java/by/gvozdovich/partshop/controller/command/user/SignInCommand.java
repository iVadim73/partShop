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
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;

public class SignInCommand implements Command {

    public SignInCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = request.getParameter(CommandVarConstant.LOGIN);
        String password = request.getParameter(CommandVarConstant.PASSWORD);
        String hashPassword = User.hashPassword(password);

        UserValidator validator = new UserValidator();
        if (validator.signinValidate(login, password)){
            if(CommandPathConstant.PATH_PAGE_SIGNIN.equals(request.getSession().getAttribute(CommandVarConstant.LAST_PATH))) {
                request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
            }
            request.getSession().removeAttribute(CommandVarConstant.LOGIN);
            request.getSession().removeAttribute(CommandVarConstant.PASSWORD);
        } else {
            request.getSession().setAttribute(CommandVarConstant.LOGIN, login);
            request.getSession().setAttribute(CommandVarConstant.PASSWORD, password);
            request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_SIGNIN);

            request.setAttribute(CommandVarConstant.CONDITION, "wrong data");

            page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            return page;
        }

//        UserValidator validator = new UserValidator();
//        if (!(validator.loginValidate(login) && validator.passwordValidate(password))) {
//            throw new ServiceException("wrong data");
//        }

        if(UserService.getInstance().signin(login, hashPassword)) {
            request.getSession().setAttribute(CommandVarConstant.USER_LOGIN, login);
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
            }
            page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "wrong login or password");
            page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
        }
        return page;
    }
}
