package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.BillInfo;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BillInfoService;
import by.gvozdovich.partshop.model.logic.BillService;
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class UpdateUserBillCommand implements Command {

    public UpdateUserBillCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();

        int userId = Integer.parseInt(request.getParameter(CommandVarConstant.USER_ID));
        String strBill = request.getParameter(CommandVarConstant.BILL);
        int billInfoId = Integer.parseInt(request.getParameter(CommandVarConstant.BILL_INFO_ID));

        UserValidator validator = new UserValidator();
        if(!validator.billValidate(strBill)) {
            throw new ServiceException("wrong data");
        }

        BigDecimal sum = new BigDecimal(strBill);
        User user = UserService.getInstance().takeUserById(userId);
        BillInfo billInfo = BillInfoService.getInstance().takeBillInfoById(billInfoId);

        String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
        if (type.equals(CommandVarConstant.ADMIN) || type.equals(CommandVarConstant.SELLER)) {
            if (BillService.getInstance().add(user, sum, billInfo)) {
                request.setAttribute(CommandVarConstant.CONDITION, "bill plus");
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "bill plus error");
            }
        }
        String path = String.valueOf(request.getRequestURL());
        page.setPage(path);

        return page;
    }
}
