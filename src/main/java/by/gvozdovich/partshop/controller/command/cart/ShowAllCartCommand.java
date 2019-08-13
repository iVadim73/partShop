package by.gvozdovich.partshop.controller.command.cart;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.CartService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * take and show all Cart from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ShowAllCartCommand implements Command {

    public ShowAllCartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to page with result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);
            List<Cart> carts = CartService.getInstance().takeCartByUserLogin(currentLogin);
            request.setAttribute(CommandVarConstant.CARTS, carts);

            page = new TagCommand().execute(request);
            page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLCART);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
