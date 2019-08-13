package by.gvozdovich.partshop.controller.command.bill;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Bill;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BillService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * show all Bill to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ShowAllBillCommand implements Command {

    public ShowAllBillCommand() {
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
            List<Bill> bills = BillService.getInstance().takeBillByUserLogin(login);
            request.setAttribute(CommandVarConstant.BILLS, bills);
            page = new TagCommand().execute(request);
            page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLBILL);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
