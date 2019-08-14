package by.gvozdovich.partshop.controller.command.order;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * add oder to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddOrderCommand implements Command {

    public AddOrderCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show order page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);
            String strCartId = request.getParameter(CommandVarConstant.CART_ID);

            int partId = Integer.valueOf(request.getParameter(CommandVarConstant.PART_ID));
            User user = UserService.getInstance().takeUserByLogin(currentLogin);
            Part part = PartService.getInstance().takePartById(partId);
            double discount = user.getDiscount();
            double ratio = (100 - discount) / 100;

            int partCount = Integer.valueOf(request.getParameter(CommandVarConstant.COUNT));
            BigDecimal price = part.getPrice();
            BigDecimal cost = price.multiply(BigDecimal.valueOf(partCount));
            BigDecimal costWithDiscount = cost.multiply(BigDecimal.valueOf(ratio));

            if (user.getBill().compareTo(costWithDiscount) >= 0) {
                int cartId = 0;
                if (strCartId != null) {
                    cartId = Integer.valueOf(strCartId);
                }

                CartService.getInstance().buy(cartId, user, part, partCount);
                request.setAttribute(CommandVarConstant.CONDITION, "card buy completed successfully");
            } else {
                logger.error("no such money");
                request.setAttribute(CommandVarConstant.CONDITION, "no such money");
            }

            String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
            int userId = user.getUserId();
            String pageToRedirect;
            page.setRouterType(Router.RouterType.REDIRECT);

            switch (userType) {
                case CommandVarConstant.ADMIN:
                case CommandVarConstant.SELLER:
                    pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWALLORDER +"?userId="+userId;
                    page.setPage(pageToRedirect);
                    break;
                default:
                    pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWALLORDER_FOR_USER +"?userId="+userId;
                    page.setPage(pageToRedirect);
                    break;
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
