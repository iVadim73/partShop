package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.BillInfo;
import by.gvozdovich.partshop.model.entity.Role;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BillInfoService;
import by.gvozdovich.partshop.model.service.RoleService;
import by.gvozdovich.partshop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * take and show User from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ShowUserForSellerAndAdminCommand implements Command {

    public ShowUserForSellerAndAdminCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to page with result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String strUserId = request.getParameter(CommandVarConstant.USER_ID);
            int userId = Integer.parseInt(strUserId);
            User user = UserService.getInstance().takeUserById(userId);

            request.setAttribute(CommandVarConstant.USER_ID, user.getUserId());
            request.setAttribute(CommandVarConstant.LOGIN, user.getLogin());
            request.setAttribute(CommandVarConstant.EMAIL, user.getEmail());
            request.setAttribute(CommandVarConstant.PHONE, user.getPhone());
            request.setAttribute(CommandVarConstant.NAME, user.getName());
            request.setAttribute(CommandVarConstant.REGISTRATION_DATE, user.getRegistrationDate());
            request.setAttribute(CommandVarConstant.DISCOUNT, user.getDiscount());
            request.setAttribute(CommandVarConstant.STAR, user.getStar());
            request.setAttribute(CommandVarConstant.BILL, user.getBill());
            request.setAttribute(CommandVarConstant.COMMENT, user.getComment());
            request.setAttribute(CommandVarConstant.IS_ACTIVE, user.getIsActive());

            page = new TagCommand().execute(request);

            List<BillInfo> billInfoList = BillInfoService.getInstance().takeAllBillInfo();
            request.setAttribute(CommandVarConstant.BILL_INFO_LIST, billInfoList);

            String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);

            switch (userType) {
                case CommandVarConstant.ADMIN:
                    request.setAttribute(CommandVarConstant.ROLE, user.getRole());
                    List<Role> roles = RoleService.getInstance().takeAllRole();
                    request.setAttribute(CommandVarConstant.ROLES, roles);
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_ADMIN);
                    break;
                case CommandVarConstant.SELLER:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_SELLER);
                    break;
                default:
                    String currentLogin = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);
                    request.setAttribute(CommandVarConstant.CONDITION, "access fail");
                    if (currentLogin == null) {
                        page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
                    } else {
                        page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
                    }
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
