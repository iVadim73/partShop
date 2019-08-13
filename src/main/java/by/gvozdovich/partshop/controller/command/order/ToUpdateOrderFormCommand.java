package by.gvozdovich.partshop.controller.command.order;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Condition;
import by.gvozdovich.partshop.model.entity.Order;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.ConditionService;
import by.gvozdovich.partshop.model.service.OrderService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * forward to update Order page
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ToUpdateOrderFormCommand implements Command {

    public ToUpdateOrderFormCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to update order page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            int orderId = Integer.parseInt(request.getParameter(CommandVarConstant.ORDER_ID));
            Order order = OrderService.getInstance().takeOrderById(orderId);
            request.setAttribute(CommandVarConstant.ORDER, order);

            List<Condition> conditions = ConditionService.getInstance().takeAllCondition();
            request.setAttribute(CommandVarConstant.CONDITIONS, conditions);

            page.setPage(CommandPathConstant.PATH_PAGE_UPDATEORDER);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
