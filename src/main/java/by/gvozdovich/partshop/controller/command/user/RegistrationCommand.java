package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Role;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.RoleService;
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private static final int defaultRoleId = 2;

    public RegistrationCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Role role = RoleService.getInstance().takeRoleById(defaultRoleId);

        Router page = new Router();

        String login = request.getParameter(CommandVarConstant.LOGIN);
        String password = request.getParameter(CommandVarConstant.PASSWORD);
        String email = request.getParameter(CommandVarConstant.EMAIL);
        String phoneStr = request.getParameter(CommandVarConstant.PHONE);
        String name = request.getParameter(CommandVarConstant.NAME);

        UserValidator validator = new UserValidator();
        if (validator.registrationValidate(login, password, email, phoneStr, name)){
            request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
            request.getSession().removeAttribute(CommandVarConstant.LOGIN);
            request.getSession().removeAttribute(CommandVarConstant.PASSWORD);
            request.getSession().removeAttribute(CommandVarConstant.EMAIL);
            request.getSession().removeAttribute(CommandVarConstant.PHONE);
            request.getSession().removeAttribute(CommandVarConstant.NAME);
            request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
        } else {
            request.getSession().setAttribute(CommandVarConstant.LOGIN, login);
            request.getSession().setAttribute(CommandVarConstant.PASSWORD, password);
            request.getSession().setAttribute(CommandVarConstant.EMAIL, email);
            request.getSession().setAttribute(CommandVarConstant.PHONE, phoneStr);
            request.getSession().setAttribute(CommandVarConstant.NAME, name);
            request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_REGISTRATION);

            request.setAttribute(CommandVarConstant.CONDITION, "wrong data");

            page.setPage(CommandPathConstant.PATH_PAGE_REGISTRATION);
            return page;
        }

//        if (!(validator.loginValidate(login) && validator.passwordValidate(password) && validator.emailValidate(email)
//                && validator.phoneValidate(phoneStr) && validator.nameValidate(name))) {
//            throw new ServiceException("wrong data");
//        }

        long phone = Long.valueOf(phoneStr.replace("+", ""));

        if(UserService.getInstance().registration(login, password, email, phone, name, role)) {
            request.setAttribute(CommandVarConstant.CONDITION, "registration completed successfully");
            page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "registration error");
            page.setPage(CommandPathConstant.PATH_PAGE_REGISTRATION);
        }
        return page;
    }
}
