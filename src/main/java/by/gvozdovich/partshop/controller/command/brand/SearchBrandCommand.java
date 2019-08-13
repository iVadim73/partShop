package by.gvozdovich.partshop.controller.command.brand;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.command.validator.BrandValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BrandService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * search Brand from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class SearchBrandCommand implements Command {

    public SearchBrandCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show all brand page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            String data = request.getParameter(CommandVarConstant.PART_OF_BRAND_NAME);
            List<Brand> brands = null;
            BrandValidator validator = new BrandValidator();
            if (data.isEmpty()) {
                brands = BrandService.getInstance().takeAllBrand();
            } else if (!validator.dataValidate(data)) {
                page = goError(request, "wrong part of brand");
            } else {
                brands = BrandService.getInstance().takeBrand(data);
            }
            request.setAttribute(CommandVarConstant.BRANDS, brands);

            page = new TagCommand().execute(request);
            page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLBRAND);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
