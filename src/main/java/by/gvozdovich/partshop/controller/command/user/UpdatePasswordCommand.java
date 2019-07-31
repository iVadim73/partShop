package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;

public class UpdatePasswordCommand implements Command {

    public UpdatePasswordCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();

        String oldPassword = request.getParameter(CommandVarConstant.OLD_PASSWORD);
        String newPassword1 = request.getParameter(CommandVarConstant.NEW_PASSWORD_1);
        String newPassword2 = request.getParameter(CommandVarConstant.NEW_PASSWORD_2);

        UserValidator validator = new UserValidator();
        if(!(validator.passwordValidate(oldPassword) && validator.passwordValidate(newPassword1) && validator.passwordValidate(newPassword2))) {
            throw new ServiceException("wrong data");
        }

        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);
        if (login == null) {
            request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");
            page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            return page;
        }
        User user = UserService.getInstance().takeUserByLogin(login);

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
        return page;
    }
}
