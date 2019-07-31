package by.gvozdovich.partshop.controller.command.order;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.*;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class AddOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    public AddOrderCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);
        String strCartId = request.getParameter(CommandVarConstant.CART_ID);

        if (login == null) {
            request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");
            page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            return page;
        }

        int partId = Integer.valueOf(request.getParameter(CommandVarConstant.PART_ID));
        User user = UserService.getInstance().takeUserByLogin(login);
        Part part = PartService.getInstance().takePartById(partId);

        int partCount = Integer.valueOf(request.getParameter(CommandVarConstant.COUNT));
        BigDecimal price = part.getPrice();
        BigDecimal cost = price.multiply(BigDecimal.valueOf(partCount));
//        Condition condition = ConditionService.getInstance().takeConditionById(1);
//        boolean isActive = true;
//        BillInfo billInfo = BillInfoService.getInstance().takeBillInfoById(1);
//
//        if (user.getBill().compareTo(cost) >= 0) {
//            if (OrderService.getInstance().add(user, part, cost, condition, partCount, isActive)) {
//                request.setAttribute(CommandVarConstant.CONDITION, "order add completed successfully");
//                if (BillService.getInstance().add(user, cost, billInfo)) {
//                    BigDecimal bill = user.getBill().subtract(cost);
//                    UserService.getInstance().updateBill(user, bill);
//                    deleteFromCart(strCartId);
//                } else {
//                    logger.error("add bill error! user:" + user + " part:" + part + " count:" + partCount + " cost:" + cost);
//                }
//
//            } else {
//                request.setAttribute(CommandVarConstant.CONDITION, "order add fail");
//            }
//        } else {
//            request.setAttribute(CommandVarConstant.CONDITION, "no such money");
//        }

        if (user.getBill().compareTo(cost) >= 0) { // TODO: 2019-07-29 норм???
            int cartId = 0;
            if (strCartId != null) {
                cartId = Integer.valueOf(strCartId);
            }
            if (CartService.getInstance().buy(cartId, user, part, partCount)) {
                request.setAttribute(CommandVarConstant.CONDITION, "card buy completed successfully");
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "cart buy fail");
            }
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "no such money");
        }

        page = new ShowAllOrderCommand().execute(request);
        return page;
    }

//    private void deleteFromCart(String strCartId) throws ServiceException {
//        if (strCartId != null) {
//            int cartId = Integer.valueOf(strCartId);
//            Cart cart = CartService.getInstance().takeCartById(cartId);
//            CartService.getInstance().delete(cart);
//        }
//    }
}
