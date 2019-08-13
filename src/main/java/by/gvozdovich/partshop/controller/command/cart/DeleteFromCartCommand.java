package by.gvozdovich.partshop.controller.command.cart;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.CartService;
import javax.servlet.http.HttpServletRequest;

/**
 * delete Cart from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class DeleteFromCartCommand implements Command {

    public DeleteFromCartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show all cart page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            int cartId = Integer.parseInt(request.getParameter(CommandVarConstant.CART_ID));
            Cart cart = CartService.getInstance().takeCartById(cartId);
            CartService.getInstance().delete(cart);
            request.setAttribute(CommandVarConstant.CONDITION, "cart deleted successfully");
            page.setRouterType(Router.RouterType.REDIRECT);
            page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLCART);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
