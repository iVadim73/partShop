package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BrandService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ToAddPartFormCommand implements Command {

    public ToAddPartFormCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        List<Brand> brands = BrandService.getInstance().takeAllBrand();
        request.setAttribute(CommandVarConstant.BRANDS, brands);
        page.setPage(CommandPathConstant.PATH_PAGE_ADDPART);
        return page;
    }
}
