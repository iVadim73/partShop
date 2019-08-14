package by.gvozdovich.partshop.controller.command.brand;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.BrandValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BrandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * update Brand on DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UpdateBrandCommand implements Command {

    public UpdateBrandCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to previous page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            int brandId = Integer.parseInt(request.getParameter(CommandVarConstant.BRAND_ID));
            String name = request.getParameter(CommandVarConstant.NAME);
            String country = request.getParameter(CommandVarConstant.COUNTRY);
            String info = request.getParameter(CommandVarConstant.INFO);
            boolean isActive = request.getParameter(CommandVarConstant.ACTIVE) != null;

            BrandValidator validator = new BrandValidator();
            if (!validator.brandValidate(name, country, info)) {
                page = goError(request, "wrong data");
                logger.warn("wrong data :" + name + " " + country + " " + info);
            } else {
                if (BrandService.getInstance().update(brandId, name, country, info, isActive)) {
                    request.setAttribute(CommandVarConstant.CONDITION, "brand updated successfully");
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "brand with this name already added");
                }
                page = new ToUpdateBrandFormCommand().execute(request);
            }
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
            logger.error("exception in Service layer :" + e);
        }

        return page;
    }
}
