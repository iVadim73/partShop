package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.PartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * change Part active status
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ActivateDeactivatePartCommand implements Command {

    public ActivateDeactivatePartCommand() {
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
            int partId = Integer.parseInt(request.getParameter(CommandVarConstant.PART_ID));
            Part part = PartService.getInstance().takePartById(partId);
            boolean isActive = part.getIsActive();

            if (PartService.getInstance().activateDeactivate(part)) {
                if (isActive) {
                    request.setAttribute(CommandVarConstant.CONDITION, "part deactivated successfully");
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "part activated successfully");
                }
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "part activated error");
            }

            String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
            page.setRouterType(Router.RouterType.REDIRECT);

            switch (userType) {
                case CommandVarConstant.ADMIN:
                case CommandVarConstant.SELLER:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART);
                    break;
                default:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART_FOR_USER);
                    break;
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
