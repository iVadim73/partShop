package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;

public class ActivateDeactivateUserCommand implements Command {

    public ActivateDeactivateUserCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        int userId = Integer.parseInt(request.getParameter(CommandVarConstant.USER_ID));
        User user = UserService.getInstance().takeUserById(userId);
        boolean isActive = user.getIsActive();

        if (UserService.getInstance().activateDeactivate(user)) {
            if(isActive) {
                request.setAttribute(CommandVarConstant.CONDITION, "user deactivated successfully");
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "user activated successfully");
            }
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "user activated error");
        }
        Router page = new ShowAllUserCommand().execute(request);
        return page;
    }
}
