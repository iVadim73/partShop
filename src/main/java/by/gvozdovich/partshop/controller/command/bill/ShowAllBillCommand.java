package by.gvozdovich.partshop.controller.command.bill;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.Bill;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BillService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllBillCommand implements Command {

    public ShowAllBillCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);
        List<Bill> bills = BillService.getInstance().takeBillByUserLogin(login);
        request.setAttribute(CommandVarConstant.BILLS, bills);

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLBILL);
        return page;
    }
}
