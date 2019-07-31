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

public class ShowUserCommand implements Command {

    public ShowUserCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);
        User user = UserService.getInstance().takeUserByLogin(login);

        request.setAttribute(CommandVarConstant.EMAIL, user.getEmail());
        request.setAttribute(CommandVarConstant.PHONE, user.getPhone());
        request.setAttribute(CommandVarConstant.NAME, user.getName());
        request.setAttribute(CommandVarConstant.DISCOUNT, user.getDiscount());
        request.setAttribute(CommandVarConstant.BILL, user.getBill());

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        page.setPage(CommandPathConstant.PATH_PAGE_SHOWUSER);
        return page;
    }
}
