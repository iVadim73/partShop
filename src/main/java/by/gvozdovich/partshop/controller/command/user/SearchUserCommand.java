package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SearchUserCommand implements Command {

    public SearchUserCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();

        String data = request.getParameter(CommandVarConstant.PART_OF_USER_LOGIN);
        List<User> users;
        UserValidator validator = new UserValidator();
        if (data.isEmpty()) {
            users = UserService.getInstance().takeAllUser();
        } else if (!validator.loginValidate(data)) {
            throw new ServiceException("wrong data");
        } else {
            users = UserService.getInstance().takeUser(data);
        }
        request.setAttribute(CommandVarConstant.USERS, users);

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);

        switch (type) {
            case CommandVarConstant.ADMIN:
                page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_ADMIN);
                break;
            case CommandVarConstant.SELLER:
                page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_SELLER);
                break;
            default:
                // access fail
                page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
                break;
        }

        return page;
    }
}
