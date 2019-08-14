package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Role;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.RoleService;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * add User to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class RegistrationCommand implements Command {
    private static final int DEFAULT_ROLE_ID = 2;

    public RegistrationCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to previous page if data is wrong
     * forward to index page if added
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            Role role = RoleService.getInstance().takeRoleById(DEFAULT_ROLE_ID);

            String login = request.getParameter(CommandVarConstant.LOGIN);
            String password = request.getParameter(CommandVarConstant.PASSWORD);
            String email = request.getParameter(CommandVarConstant.EMAIL);
            String phoneStr = request.getParameter(CommandVarConstant.PHONE);
            String name= null;
            try {
                name = new String(request.getParameter(CommandVarConstant.NAME).getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e);
            }

            UserValidator validator = new UserValidator();
            String valid = validator.registrationValidate(login, password, email, phoneStr, name);
            if (!valid.isEmpty()) {
                logger.error("wrong data :" + login + " " + password + " " + email + " " + phoneStr + " " + name);
                request.getSession().setAttribute(CommandVarConstant.LOGIN, login);
                request.getSession().setAttribute(CommandVarConstant.PASSWORD, password);
                request.getSession().setAttribute(CommandVarConstant.EMAIL, email);
                request.getSession().setAttribute(CommandVarConstant.PHONE, phoneStr);
                request.getSession().setAttribute(CommandVarConstant.NAME, name);
                request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_REGISTRATION);

                request.setAttribute(CommandVarConstant.CONDITION, valid);
                page.setPage(CommandPathConstant.PATH_PAGE_REGISTRATION);
            } else {
                request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
                request.getSession().removeAttribute(CommandVarConstant.LOGIN);
                request.getSession().removeAttribute(CommandVarConstant.PASSWORD);
                request.getSession().removeAttribute(CommandVarConstant.EMAIL);
                request.getSession().removeAttribute(CommandVarConstant.PHONE);
                request.getSession().removeAttribute(CommandVarConstant.NAME);
                request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);

                long phone = Long.valueOf(phoneStr.replace("+", ""));

                if (UserService.getInstance().registrate(login, password, email, phone, name, role)) {
                    request.setAttribute(CommandVarConstant.CONDITION, "registration completed successfully");
                    page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "user with this login or email already registered");
                    page.setPage(CommandPathConstant.PATH_PAGE_REGISTRATION);
                }
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
