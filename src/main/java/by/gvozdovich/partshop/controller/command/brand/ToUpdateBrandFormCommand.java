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
 * forward to update brand page
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ToUpdateBrandFormCommand implements Command {

    public ToUpdateBrandFormCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to update brand page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            int brandId = Integer.parseInt(request.getParameter(CommandVarConstant.BRAND_ID));
            Brand brand = BrandService.getInstance().takeBrandById(brandId);
            String name = brand.getName();
            String country = brand.getCountry();
            String info = brand.getInfo();
            boolean isActive = brand.getIsActive();

            request.setAttribute(CommandVarConstant.BRAND_ID, brandId);
            request.setAttribute(CommandVarConstant.NAME, name);
            request.setAttribute(CommandVarConstant.COUNTRY, country);
            request.setAttribute(CommandVarConstant.INFO, info);
            request.setAttribute(CommandVarConstant.IS_ACTIVE, isActive);

            page.setPage(CommandPathConstant.PATH_PAGE_UPDATEBRAND);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
