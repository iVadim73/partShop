package by.gvozdovich.partshop.controller.command.cart;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.CartValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.CartService;
import javax.servlet.http.HttpServletRequest;

public class UpdateCartCommand implements Command {

    public UpdateCartCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String strCount = request.getParameter(CommandVarConstant.COUNT);

        CartValidator validator = new CartValidator();
        if (!validator.countValidate(strCount)) {
            throw new ServiceException("wrong data");
        }

        int count = Integer.parseInt(strCount);
        if (count == 0) {
            request.setAttribute(CommandVarConstant.CONDITION, "count must be positive");
        } else {
            int cartId = Integer.parseInt(request.getParameter(CommandVarConstant.CART_ID));
            Cart cart = CartService.getInstance().takeCartById(cartId);

            if (CartService.getInstance().update(cartId, cart.getUser(), cart.getPart(), count)) {
                request.setAttribute(CommandVarConstant.CONDITION, "cart updated successfully");
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "cart updated error");
            }
        }
        Router page = new ShowAllCartCommand().execute(request);
        return page;
    }
}
