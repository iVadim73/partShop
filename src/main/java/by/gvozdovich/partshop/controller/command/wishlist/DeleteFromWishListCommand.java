package by.gvozdovich.partshop.controller.command.wishlist;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.WishList;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.WishListService;
import javax.servlet.http.HttpServletRequest;

public class DeleteFromWishListCommand implements Command {

    public DeleteFromWishListCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        int wishListId = Integer.parseInt(request.getParameter(CommandVarConstant.WISH_LIST_ID));
        WishList wishList = WishListService.getInstance().takeWishListById(wishListId);

        if (WishListService.getInstance().delete(wishList)) {
            request.setAttribute(CommandVarConstant.CONDITION, "wish list deleted successfully");
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "wish list deleted error");
        }
        Router page = new ShowAllWishListCommand().execute(request);
        return page;
    }
}
