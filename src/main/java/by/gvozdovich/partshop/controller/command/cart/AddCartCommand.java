package by.gvozdovich.partshop.controller.command.cart;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.CartValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.entity.WishList;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.CartService;
import by.gvozdovich.partshop.model.service.PartService;
import by.gvozdovich.partshop.model.service.UserService;
import by.gvozdovich.partshop.model.service.WishListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * add Cart to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddCartCommand implements Command {

    public AddCartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to signin page if user == null
     * forward to show all cart page if added
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);

            int partId;
            int count;
            if (CommandPathConstant.PATH_PAGE_SIGNIN.equals(request.getSession().getAttribute(CommandVarConstant.LAST_PATH))) {
                partId = (int) request.getSession().getAttribute(CommandVarConstant.PART_ID);
                count = (int) request.getSession().getAttribute(CommandVarConstant.COUNT);
            } else {
                partId = Integer.valueOf(request.getParameter(CommandVarConstant.PART_ID));
                String strCount = request.getParameter(CommandVarConstant.COUNT);
                CartValidator validator = new CartValidator();
                if (!(validator.countValidate(strCount))) {
                    logger.error("wrong count :" + strCount);
                    return goError(request, "wrong data");
                }
                count = Integer.valueOf(strCount);
            }

            Part part = PartService.getInstance().takePartById(partId);

            if (currentLogin == null) {
                request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");

                request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_SHOWALLPART);
                request.getSession().setAttribute(CommandVarConstant.ACTION, CommandVarConstant.ADD_CART);
                request.getSession().setAttribute(CommandVarConstant.PART_ID, partId);
                request.getSession().setAttribute(CommandVarConstant.COUNT, count);

                page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            } else {
                request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
                request.getSession().removeAttribute(CommandVarConstant.PART_ID);
                request.getSession().removeAttribute(CommandVarConstant.ACTION);
                request.getSession().removeAttribute(CommandVarConstant.COUNT);

                User user = UserService.getInstance().takeUserByLogin(currentLogin);
                String strWishListId = request.getParameter(CommandVarConstant.WISH_LIST_ID);

                try {
                    Cart cart = CartService.getInstance().takeCartByPartIdAndUserId(partId, user.getUserId());
                    int newCount = cart.getCount() + count;
                    CartService.getInstance().update(cart.getCartId(), user, part, newCount);
                    request.setAttribute(CommandVarConstant.CONDITION, "cart update completed successfully");
                    deleteFromWishList(strWishListId);
                } catch (ServiceException e) {
                    CartService.getInstance().add(user, part, count);
                }

                page.setRouterType(Router.RouterType.REDIRECT);
                page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLCART);
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }

    private void deleteFromWishList(String strWishListId) throws ServiceException {
        if (strWishListId != null) {
            int wishListId = Integer.valueOf(strWishListId);
            WishList wishList = WishListService.getInstance().takeWishListById(wishListId);
            WishListService.getInstance().delete(wishList);
        }
    }
}
