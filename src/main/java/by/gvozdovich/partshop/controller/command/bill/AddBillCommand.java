package by.gvozdovich.partshop.controller.command.bill;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.BillValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.BillInfo;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class AddBillCommand implements Command {

    public AddBillCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);

        if (login == null) {
            request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");
            page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            return page;
        }

        String strSum = request.getParameter(CommandVarConstant.SUM);

        BillValidator validator = new BillValidator();
        if (!validator.sumValidate(strSum)) {
            throw new ServiceException("wrong data");
        }

        User user = UserService.getInstance().takeUserByLogin(login);
        BigDecimal sum = new BigDecimal(strSum);
        BillInfo billInfo = BillInfoService.getInstance().takeBillInfoById(1);

        if (BillService.getInstance().add(user, sum, billInfo)) {
            request.setAttribute(CommandVarConstant.CONDITION, "bill add completed successfully");
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "bill add fail");
        }
        page = new ShowAllBillCommand().execute(request);
        return page;
    }
}
