package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * update User password on DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UpdatePasswordCommand implements Command {

    public UpdatePasswordCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to previous page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String oldPassword = request.getParameter(CommandVarConstant.OLD_PASSWORD);
            String newPassword1 = request.getParameter(CommandVarConstant.NEW_PASSWORD_1);
            String newPassword2 = request.getParameter(CommandVarConstant.NEW_PASSWORD_2);

            UserValidator validator = new UserValidator();
            if (!(validator.passwordValidate(oldPassword) && validator.passwordValidate(newPassword1) && validator.passwordValidate(newPassword2))) {
                page = goError(request, "wrong data");
                logger.error("wrong data :" + oldPassword + " " + newPassword1 + " " + newPassword2);
            } else {
                String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);
                if (currentLogin == null) {
                    request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");
                    page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
                } else {
                    User user = UserService.getInstance().takeUserByLogin(currentLogin);

                    String oldHashPassword = User.hashPassword(oldPassword);
                    if (!oldHashPassword.equals(user.getPassword())) {
                        request.setAttribute(CommandVarConstant.CONDITION, "wrong old password");
                    } else if (!newPassword1.equals(newPassword2)) {
                        request.setAttribute(CommandVarConstant.CONDITION, "new passwords don't equals");
                    } else if (UserService.getInstance().updatePassword(user, newPassword1)) {
                        request.setAttribute(CommandVarConstant.CONDITION, "password updated successfully");
                    } else {
                        request.setAttribute(CommandVarConstant.CONDITION, "password updated error");
                    }

                    page = new ShowUserCommand().execute(request);
                }
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
