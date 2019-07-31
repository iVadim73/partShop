package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Role;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.RoleService;
import by.gvozdovich.partshop.model.logic.UserService;
import javax.servlet.http.HttpServletRequest;

public class UpdateUserDataForAdminCommand implements Command {

    public UpdateUserDataForAdminCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {

        String strPhone = request.getParameter(CommandVarConstant.PHONE);
        String name = request.getParameter(CommandVarConstant.NAME);
        String strDiscount = request.getParameter(CommandVarConstant.DISCOUNT);
        String strStar = request.getParameter(CommandVarConstant.STAR);
        String strRoleId = request.getParameter(CommandVarConstant.ROLE_ID);
        String comment = request.getParameter(CommandVarConstant.COMMENT);

        UserValidator validator = new UserValidator();
        if (!validator.updateValidate(strPhone, name, strDiscount, strStar, strRoleId, comment)) {
            throw new ServiceException("wrong data");
        }

        int userId = Integer.parseInt(request.getParameter(CommandVarConstant.USER_ID));
        long phone = Long.parseLong(strPhone);
        double discount = Double.valueOf(strDiscount);
        int star = Integer.parseInt(strStar);
        int roleId = Integer.parseInt(strRoleId);
        Role role = RoleService.getInstance().takeRoleById(roleId);
        String active = request.getParameter(CommandVarConstant.ACTIVE);
        boolean isActive = active != null;

        if (UserService.getInstance().update(userId, phone, name, discount, star, role, comment, isActive)) {
            request.setAttribute(CommandVarConstant.CONDITION, "user updated successfully");
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "user updated error");
        }
        Router page = new ShowAllUserCommand().execute(request);

        return page;
    }
}
