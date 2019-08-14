package by.gvozdovich.partshop.controller.command.cart;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.CartValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * update Cart on DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UpdateCartCommand implements Command {

    public UpdateCartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to previous page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String strCount = request.getParameter(CommandVarConstant.COUNT);

            CartValidator validator = new CartValidator();
            if (!validator.countValidate(strCount)) {
                logger.error("wrong count :" + strCount);
                page = goError(request, "wrong data");
            } else {
                int count = Integer.parseInt(strCount);
                if (count == 0) {
                    request.setAttribute(CommandVarConstant.CONDITION, "count must be positive");
                } else {
                    int cartId = Integer.parseInt(request.getParameter(CommandVarConstant.CART_ID));
                    Cart cart = CartService.getInstance().takeCartById(cartId);
                    CartService.getInstance().update(cartId, cart.getUser(), cart.getPart(), count);
                    request.setAttribute(CommandVarConstant.CONDITION, "cart updated successfully");
                }
                page = new ShowAllCartCommand().execute(request);
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
