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

public class UpdateUserDataCommand implements Command {

    public UpdateUserDataCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();

        String strPhone = request.getParameter(CommandVarConstant.PHONE);
        String name = request.getParameter(CommandVarConstant.NAME);

        UserValidator validator = new UserValidator();
        if(!(validator.phoneValidate(strPhone) && validator.nameValidate(name))) {
            throw new ServiceException("wrong data");
        }

        long phone = Long.valueOf(strPhone);
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);
        if (login == null) {
            request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");
            page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            return page;
        }
        User user = UserService.getInstance().takeUserByLogin(login);

        if (UserService.getInstance().updatePhone(user, phone)) {
            user = UserService.getInstance().takeUserByLogin(login);
            if (UserService.getInstance().updateName(user, name)) {
                request.setAttribute(CommandVarConstant.CONDITION, "name and phone updated successfully");
            }
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "name and phone updated error");
        }

        page = new ShowUserCommand().execute(request);
        return page;
    }
}
