package by.gvozdovich.partshop.controller.command.cart;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.CartService;

import javax.servlet.http.HttpServletRequest;

public class DeleteFromCartCommand implements Command {

    public DeleteFromCartCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        int cartId = Integer.parseInt(request.getParameter(CommandVarConstant.CART_ID));
        Cart cart = CartService.getInstance().takeCartById(cartId);

        if (CartService.getInstance().delete(cart)) {
            request.setAttribute(CommandVarConstant.CONDITION, "cart deleted successfully");
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "cart deleted error");
        }
        Router page = new ShowAllCartCommand().execute(request);
        return page;
    }
}
