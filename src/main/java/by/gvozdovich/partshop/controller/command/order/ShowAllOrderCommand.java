package by.gvozdovich.partshop.controller.command.order;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Order;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.OrderService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * take and show all Order from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ShowAllOrderCommand implements Command {

    public ShowAllOrderCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to page with result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            String login = request.getParameter(CommandVarConstant.LOGIN);
            request.setAttribute(CommandVarConstant.LOGIN, login);

            page = new TagCommand().execute(request);

            String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
            switch (userType) {
                case CommandVarConstant.ADMIN:
                case CommandVarConstant.SELLER:
                    if (login == null || login.isEmpty()) {
                        List<Order> orders = OrderService.getInstance().takeAllOrder();
                        request.setAttribute(CommandVarConstant.ORDERS, orders);
                    } else {
                        List<Order> orders = OrderService.getInstance().takeOrderByUserLogin(login);
                        request.setAttribute(CommandVarConstant.ORDERS, orders);
                    }
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLORDER);
                    break;
                default:
                    List<Order> orders = OrderService.getInstance().takeOrderByUserLoginForUser(login);
                    request.setAttribute(CommandVarConstant.ORDERS, orders);
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLORDER_FOR_USER);
                    break;
            }
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
