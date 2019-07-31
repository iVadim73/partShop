package by.gvozdovich.partshop.controller.command.brand;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BrandService;
import javax.servlet.http.HttpServletRequest;

public class ActivateDeactivateBrandCommand implements Command {

    public ActivateDeactivateBrandCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        int brandId = Integer.parseInt(request.getParameter(CommandVarConstant.BRAND_ID));
        Brand brand = BrandService.getInstance().takeBrandById(brandId);
        boolean isActive = brand.getIsActive();

        if (BrandService.getInstance().activateDeactivate(brand)) {
            if(isActive) {
                request.setAttribute(CommandVarConstant.CONDITION, "brand deactivated successfully");
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "brand activated successfully");
            }
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "brand activated error");
        }
        Router page = new ShowAllBrandCommand().execute(request);
        return page;
    }
}
