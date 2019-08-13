package by.gvozdovich.partshop.controller.command.brand;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BrandService;
import javax.servlet.http.HttpServletRequest;

/**
 * change Brand active status
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ActivateDeactivateBrandCommand implements Command {

    public ActivateDeactivateBrandCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to page with result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            int brandId = Integer.parseInt(request.getParameter(CommandVarConstant.BRAND_ID));
            Brand brand = BrandService.getInstance().takeBrandById(brandId);
            boolean isActive = brand.getIsActive();

            if (BrandService.getInstance().activateDeactivate(brand)) {
                if (isActive) {
                    request.setAttribute(CommandVarConstant.CONDITION, "brand deactivated successfully");
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "brand activated successfully");
                }
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "brand activated error");
            }

            page.setRouterType(Router.RouterType.REDIRECT);
            page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLBRAND);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
