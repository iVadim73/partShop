package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.UserService;
import javax.servlet.http.HttpServletRequest;

/**
 * update User on DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UpdateUserDataForSellerCommand implements Command {

    public UpdateUserDataForSellerCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to previous page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            String strPhone = request.getParameter(CommandVarConstant.PHONE);
            String name = request.getParameter(CommandVarConstant.NAME);
            String strDiscount = request.getParameter(CommandVarConstant.DISCOUNT);
            String strStar = request.getParameter(CommandVarConstant.STAR);
            String comment = request.getParameter(CommandVarConstant.COMMENT);

            UserValidator validator = new UserValidator();
            if (!validator.updateValidateForSeller(strPhone, name, strDiscount, strStar, comment)) {
                page = goError(request, "wrong data");
            } else {
                int userId = Integer.parseInt(request.getParameter(CommandVarConstant.USER_ID));
                long phone = Long.parseLong(strPhone);
                double discount = Double.valueOf(strDiscount);
                int star = Integer.parseInt(strStar);

                if (UserService.getInstance().update(userId, phone, name, discount, star, comment)) {
                    request.setAttribute(CommandVarConstant.CONDITION, "user updated successfully");
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "user updated error");
                }

                page = new ShowUserForSellerAndAdminCommand().execute(request);
            }
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
