package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllUserCommand implements Command {

    public ShowAllUserCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();

        String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
        List<User> users = null;

        switch (type) {
            case CommandVarConstant.ADMIN :
                users = UserService.getInstance().takeAllUser();
                page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_ADMIN);
                break;
            case CommandVarConstant.SELLER :
                users = UserService.getInstance().takeAllUserForSeller();
                page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_SELLER);
                break;
            default:
                //access fail
                page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
                break;
        }

        request.setAttribute(CommandVarConstant.USERS, users);

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        return page;
    }
}
