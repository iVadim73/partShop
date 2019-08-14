package by.gvozdovich.partshop.controller.command.wishlist;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.WishList;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.WishListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * delete WishList from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class DeleteFromWishListCommand implements Command {

    public DeleteFromWishListCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show all wish list page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            int wishListId = Integer.parseInt(request.getParameter(CommandVarConstant.WISH_LIST_ID));
            WishList wishList = WishListService.getInstance().takeWishListById(wishListId);

            WishListService.getInstance().delete(wishList);
            request.setAttribute(CommandVarConstant.CONDITION, "wish list deleted successfully");

            page = new ShowAllWishListCommand().execute(request);
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
