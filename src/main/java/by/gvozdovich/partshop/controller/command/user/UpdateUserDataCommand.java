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
import java.io.UnsupportedEncodingException;

/**
 * update User on DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UpdateUserDataCommand implements Command {

    public UpdateUserDataCommand() {
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
            String name = null;
            try {
                name = new String(request.getParameter(CommandVarConstant.NAME).getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e);
            }
            String strPhone = request.getParameter(CommandVarConstant.PHONE);

            UserValidator validator = new UserValidator();
            if (!(validator.phoneValidate(strPhone) && validator.nameValidate(name))) {
                page = goError(request, "wrong data");
                logger.error("wrong data :" + strPhone + " " + name);
            } else {
                long phone = Long.valueOf(strPhone);
                String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);

                if (currentLogin == null) {
                    request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");
                    page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
                } else {
                    User user = UserService.getInstance().takeUserByLogin(currentLogin);

                    if (UserService.getInstance().updatePhone(user, phone)) {
                        user = UserService.getInstance().takeUserByLogin(currentLogin);
                        if (UserService.getInstance().updateName(user, name)) {
                            request.setAttribute(CommandVarConstant.CONDITION, "name and phone updated successfully");
                        } else {
                            request.setAttribute(CommandVarConstant.CONDITION, "name updated error");
                        }
                    } else {
                        request.setAttribute(CommandVarConstant.CONDITION, "phone updated error");
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
