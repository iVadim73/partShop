package by.gvozdovich.partshop.controller.command.order;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Condition;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.ConditionService;
import by.gvozdovich.partshop.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * update Order on DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UpdateOrderCommand implements Command {

    public UpdateOrderCommand() {
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
            int orderId = Integer.parseInt(request.getParameter(CommandVarConstant.ORDER_ID));
            int conditionId = Integer.valueOf(request.getParameter(CommandVarConstant.CONDITION_ID));
            Condition condition = ConditionService.getInstance().takeConditionById(conditionId);
            String active = request.getParameter(CommandVarConstant.ACTIVE);
            boolean isActive = active != null;

            OrderService.getInstance().update(orderId, condition, isActive);
            request.setAttribute(CommandVarConstant.CONDITION, "order updated successfully");

            page = new ToUpdateOrderFormCommand().execute(request);
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
