package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

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
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String strPhone = request.getParameter(CommandVarConstant.PHONE);
            String strDiscount = request.getParameter(CommandVarConstant.DISCOUNT);
            String strStar = request.getParameter(CommandVarConstant.STAR);
            String name = null;
            String comment = null;
            try {
                name = new String(request.getParameter(CommandVarConstant.NAME).getBytes("ISO-8859-1"),"UTF-8");
                comment = new String(request.getParameter(CommandVarConstant.COMMENT).getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e);
            }

            UserValidator validator = new UserValidator();
            if (!validator.updateValidateForSeller(strPhone, name, strDiscount, strStar, comment)) {
                page = goError(request, "wrong data");
                logger.error("wrong data :" + strPhone + " " + name + " " + strDiscount + " "
                        + strStar + " " + comment);
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
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
