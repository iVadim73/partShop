package by.gvozdovich.partshop.controller.command.brand;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.BrandValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BrandService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SearchBrandCommand implements Command {

    public SearchBrandCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String data = request.getParameter(CommandVarConstant.PART_OF_BRAND_NAME);
        List<Brand> brands;
        BrandValidator validator = new BrandValidator();
        if (data.isEmpty()) {
            brands = BrandService.getInstance().takeAllBrand();
        } else if (!validator.dataValidate(data)) {
            throw new ServiceException("wrong data");
        } else {
            brands = BrandService.getInstance().takeBrand(data);
        }
        request.setAttribute(CommandVarConstant.BRANDS, brands);

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLBRAND);
        return page;
    }
}
