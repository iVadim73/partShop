package by.gvozdovich.partshop.controller.command.bill;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.BillValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.BillInfo;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * add Bill to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddBillCommand implements Command {
    private static final int withdrawBillInfoId = 1;
    private static final int correctBillInfoId = 3;

    public AddBillCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to previous page with adding result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();
        try {
            String strSum = request.getParameter(CommandVarConstant.SUM);
            strSum = strSum.replaceAll(",", ".");

            BillValidator validator = new BillValidator();
            if (!validator.sumValidate(strSum)) {
                logger.warn("wrong sum :" + strSum);
                page = goError(request, "wrong sum");
            } else {
                BigDecimal sum = new BigDecimal(strSum);
                int userId = Integer.parseInt(request.getParameter(CommandVarConstant.USER_ID));
                int billInfoId = Integer.parseInt(request.getParameter(CommandVarConstant.BILL_INFO_ID));
                User user;
                BillInfo billInfo;
                user = UserService.getInstance().takeUserById(userId);
                billInfo = BillInfoService.getInstance().takeBillInfoById(billInfoId);

                if (withdrawBillInfoId == billInfoId) {
                    sum = sum.negate();
                }

                if (sum.compareTo(BigDecimal.ZERO) == 0) {
                    request.setAttribute(CommandVarConstant.CONDITION, "sum can't be 0");
                } else if ((sum.compareTo(BigDecimal.ZERO) < 0) && correctBillInfoId != billInfoId && withdrawBillInfoId != billInfoId) {
                    request.setAttribute(CommandVarConstant.CONDITION, "sum can't be negative");
                } else if ((sum.compareTo(BigDecimal.ZERO) > 0) && withdrawBillInfoId == billInfoId) {
                    request.setAttribute(CommandVarConstant.CONDITION, "withdraw can't be negative");
                } else {
                    BillService.getInstance().add(user, sum, billInfo);
                    request.setAttribute(CommandVarConstant.CONDITION, "bill add completed successfully");
                }

                String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
                String pageToRedirect;
                page.setRouterType(Router.RouterType.REDIRECT);

                switch (userType) {
                    case CommandVarConstant.ADMIN:
                        pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_ADMIN +"?userId="+userId;
                        page.setPage(pageToRedirect);
                        break;
                    case CommandVarConstant.SELLER:
                        pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_SELLER +"?userId="+userId;
                        page.setPage(pageToRedirect);
                        break;
                    default:
                        page = goError(request, "access fail");
                        break;
                }
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page = goError(request, "get user or bill info fail");
        }

        return page;
    }
}
