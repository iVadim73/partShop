package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BrandService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * forward to add part page
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ToAddPartFormCommand implements Command {

    public ToAddPartFormCommand() {
    }

    /**
     * @return String URI page that
     * forward to add part page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            List<Brand> brands = BrandService.getInstance().takeAllBrand();
            request.setAttribute(CommandVarConstant.BRANDS, brands);
            page.setPage(CommandPathConstant.PATH_PAGE_ADDPART);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
