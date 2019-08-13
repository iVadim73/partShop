package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.UserService;
import javax.servlet.http.HttpServletRequest;

/**
 * take and show User from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ShowUserCommand implements Command {

    public ShowUserCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to page with result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);
            User user = UserService.getInstance().takeUserByLogin(currentLogin);

            request.setAttribute(CommandVarConstant.EMAIL, user.getEmail());
            request.setAttribute(CommandVarConstant.PHONE, user.getPhone());
            request.setAttribute(CommandVarConstant.NAME, user.getName());
            request.setAttribute(CommandVarConstant.DISCOUNT, user.getDiscount());
            request.setAttribute(CommandVarConstant.BILL, user.getBill());

            page = new TagCommand().execute(request);
            page.setPage(CommandPathConstant.PATH_PAGE_SHOWUSER);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
