package by.gvozdovich.partshop.controller.command.wishlist;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.PartService;
import by.gvozdovich.partshop.model.logic.UserService;
import by.gvozdovich.partshop.model.logic.WishListService;
import javax.servlet.http.HttpServletRequest;

public class AddWishListCommand implements Command {

    public AddWishListCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);

        int partId;
        if (CommandPathConstant.PATH_PAGE_SIGNIN.equals(request.getSession().getAttribute(CommandVarConstant.LAST_PATH))) {
            partId = (int) request.getSession().getAttribute(CommandVarConstant.PART_ID);
        } else {
            partId = Integer.valueOf(request.getParameter(CommandVarConstant.PART_ID));
        }
        Part part = PartService.getInstance().takePartById(partId);

        if (login == null) {
            request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");

            request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_SHOWALLPART);
            request.getSession().setAttribute(CommandVarConstant.ACTION, CommandVarConstant.ADD_WISH_LIST);
            request.getSession().setAttribute(CommandVarConstant.PART_ID, partId);

            page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            return page;
        } else {
            request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
            request.getSession().removeAttribute(CommandVarConstant.ACTION);
            request.getSession().removeAttribute(CommandVarConstant.PART_ID);
        }

        User user = UserService.getInstance().takeUserByLogin(login);

        if (WishListService.getInstance().add(user, part)) {
            request.setAttribute(CommandVarConstant.CONDITION, "wishlist add completed successfully");
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "wishlist already added");
        }
        page = new ShowAllWishListCommand().execute(request);
        return page;
    }
}
