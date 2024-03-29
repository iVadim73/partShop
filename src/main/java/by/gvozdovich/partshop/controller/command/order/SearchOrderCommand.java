package by.gvozdovich.partshop.controller.command.order;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.command.validator.OrderValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Order;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * search Order from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class SearchOrderCommand implements Command {

    public SearchOrderCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show all order page if data is wrong
     * forward to show order page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            page = new TagCommand().execute(request);

            int pageCount = (int) request.getAttribute(CommandVarConstant.PAGE_COUNT);
            String strOrderId = request.getParameter(CommandVarConstant.ORDER_ID);
            OrderValidator validator = new OrderValidator();

            List<Order> orderList = OrderService.getInstance().takeAllOrder(pageCount);
            request.setAttribute(CommandVarConstant.ORDERS, orderList);

            if (!validator.orderIdValidate(strOrderId)) {
                page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLORDER);
                logger.error("wrong orderId :" + strOrderId);
            } else {
                int orderId = Integer.parseInt(strOrderId);
                try {
                    OrderService.getInstance().takeOrderById(orderId);
                    request.setAttribute(CommandVarConstant.ORDER_ID, orderId);
                    page = new ToUpdateOrderFormCommand().execute(request);
                } catch (ServiceException e) {
                    logger.error("Order with id not found:" +  orderId);
                    request.setAttribute(CommandVarConstant.CONDITION, "Order with id:" + orderId + " not found!");
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLORDER);
                }
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
