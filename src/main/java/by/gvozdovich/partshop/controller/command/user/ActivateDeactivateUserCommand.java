package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.UserService;
import javax.servlet.http.HttpServletRequest;

/**
 * change User active status
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ActivateDeactivateUserCommand implements Command {

    public ActivateDeactivateUserCommand() {
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
            int userId = Integer.parseInt(request.getParameter(CommandVarConstant.USER_ID));
            User user = UserService.getInstance().takeUserById(userId);
            boolean isActive = user.getIsActive();

            if (UserService.getInstance().activateDeactivate(user)) {
                if (isActive) {
                    request.setAttribute(CommandVarConstant.CONDITION, "user deactivated successfully");
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "user activated successfully");
                }
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "user activated error");
            }

            String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
            page.setRouterType(Router.RouterType.REDIRECT);

            switch (userType) {
                case CommandVarConstant.SELLER:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_SELLER);
                    break;
                case CommandVarConstant.ADMIN:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_ADMIN);
                    break;
                default:
                    page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
                    break;
            }
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
