package by.gvozdovich.partshop.controller.command.brand;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BrandService;
import javax.servlet.http.HttpServletRequest;

public class ToUpdateBrandFormCommand implements Command {

    public ToUpdateBrandFormCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
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
        return page;
    }
}
