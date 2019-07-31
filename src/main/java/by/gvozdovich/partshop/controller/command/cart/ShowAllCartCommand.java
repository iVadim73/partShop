package by.gvozdovich.partshop.controller.command.cart;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.CartService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllCartCommand implements Command {

    public ShowAllCartCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);
        List<Cart> carts = CartService.getInstance().takeCartByUserLogin(login);
        request.setAttribute(CommandVarConstant.CARTS, carts);

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLCART);
        return page;
    }
}
