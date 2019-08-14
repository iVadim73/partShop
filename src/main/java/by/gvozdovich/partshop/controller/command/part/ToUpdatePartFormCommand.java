package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BrandService;
import by.gvozdovich.partshop.model.service.PartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * forward to update part page
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ToUpdatePartFormCommand implements Command {

    public ToUpdatePartFormCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to update part page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            List<Brand> brands = BrandService.getInstance().takeAllBrand();
            request.setAttribute(CommandVarConstant.BRANDS, brands);

            int partId = (int) request.getAttribute(CommandVarConstant.PART_ID);
            Part part = PartService.getInstance().takePartById(partId);

            request.setAttribute(CommandVarConstant.PART, part);
            page.setPage(CommandPathConstant.PATH_PAGE_SHOWPART);
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
