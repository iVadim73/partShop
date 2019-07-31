package by.gvozdovich.partshop.controller.command.wishlist;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.WishList;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.WishListService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllWishListCommand implements Command {

    public ShowAllWishListCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);
        List<WishList> wishLists = WishListService.getInstance().takeWishListByUserLogin(login);
        request.setAttribute(CommandVarConstant.WISH_LISTS, wishLists);

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLWISHLIST);
        return page;
    }
}
