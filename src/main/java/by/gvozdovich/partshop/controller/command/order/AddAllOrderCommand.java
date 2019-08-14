package by.gvozdovich.partshop.controller.command.order;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.CartService;
import by.gvozdovich.partshop.model.service.PartService;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * add all oder to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddAllOrderCommand implements Command {

    public AddAllOrderCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show all order page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);

            if (currentLogin == null) {
                logger.error("login is null");
                page = goError(request, "You have to sign in!");
            } else {
                List<Cart> carts = CartService.getInstance().takeCartByUserLogin(currentLogin);
                User user = UserService.getInstance().takeUserByLogin(currentLogin);
                double discount = user.getDiscount();
                double ratio = (100 - discount) / 100;
                BigDecimal totalSum = BigDecimal.ZERO;

                for (Cart cart : carts) {
                    int partId = cart.getPart().getPartId();
                    int partCount = cart.getCount();
                    Part part = PartService.getInstance().takePartById(partId);
                    BigDecimal price = part.getPrice();
                    BigDecimal cost = price.multiply(BigDecimal.valueOf(partCount));
                    BigDecimal costWithDiscount = cost.multiply(BigDecimal.valueOf(ratio));
                    totalSum = totalSum.add(costWithDiscount);
                }

                if (user.getBill().compareTo(totalSum) >= 0) {
                    for (Cart cart : carts) {
                        int cartId = cart.getCartId();
                        Part part = cart.getPart();
                        int partCount = cart.getCount();

                        if (!CartService.getInstance().buy(cartId, user, part, partCount)) {
                            request.setAttribute(CommandVarConstant.CONDITION, "card buy completed successfully");
                        } else {
                            logger.error("buy fail :" + cartId + " " + user + " " + part + " " + partCount);
                            request.setAttribute(CommandVarConstant.CONDITION, "cart buy fail");
                        }
                    }
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "no such money");
                }

                int userId = user.getUserId();
                String pageToRedirect;
                page.setRouterType(Router.RouterType.REDIRECT);
                String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);

                switch (userType) {
                    case CommandVarConstant.SELLER:
                    case CommandVarConstant.ADMIN:
                        pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWALLORDER +"?userId="+userId;
                        page.setPage(pageToRedirect);
                        break;
                    default:
                        pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWALLORDER_FOR_USER +"?userId="+userId;
                        page.setPage(pageToRedirect);
                        break;
                }
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
