package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.BillInfo;
import by.gvozdovich.partshop.model.entity.Role;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BillInfoService;
import by.gvozdovich.partshop.model.logic.RoleService;
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUserForSellerAndAdminCommand implements Command {

    public ShowUserForSellerAndAdminCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String strUserId = request.getParameter(CommandVarConstant.USER_ID);

        if (strUserId == null) { // TODO: 2019-07-30 нужно ли??? 
            throw new ServiceException("wrong userId : null");
        }
        
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
        request.setAttribute(CommandVarConstant.ROLE, user.getRole());
        request.setAttribute(CommandVarConstant.COMMENT, user.getComment());
        request.setAttribute(CommandVarConstant.IS_ACTIVE, user.getIsActive());

        List<Role> roles = RoleService.getInstance().takeAllRole();
        request.setAttribute(CommandVarConstant.ROLES, roles);

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        List<BillInfo> billInfoList = BillInfoService.getInstance().takeAllBillInfo();
        request.setAttribute(CommandVarConstant.BILL_INFO_LIST, billInfoList);

        page.setPage(CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_ADMIN);
        return page;
    }
}
