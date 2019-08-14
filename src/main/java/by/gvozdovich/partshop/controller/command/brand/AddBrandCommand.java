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
import java.io.UnsupportedEncodingException;

/**
 * add Brand to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddBrandCommand implements Command {

    public AddBrandCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to previous page if data is wrong
     * forward to show all brand page if added
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String info= null;
            try {
                info = new String(request.getParameter(CommandVarConstant.INFO).getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e);
            }
            String name = request.getParameter(CommandVarConstant.NAME);
            String country = request.getParameter(CommandVarConstant.COUNTRY);
            String active = request.getParameter(CommandVarConstant.ACTIVE);
            boolean isActive = active != null;

            BrandValidator validator = new BrandValidator();
            if (!validator.brandValidate(name, country, info)) {
                request.setAttribute(CommandVarConstant.CONDITION, "wrong data");
                logger.warn("wrong data :" + name + " " + country + " " + info);
                page = new ToAddBrandFormCommand().execute(request);
            } else {
                if (BrandService.getInstance().add(name, country, info, isActive)) {
                    request.setAttribute(CommandVarConstant.CONDITION, "brand add completed successfully");
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "brand with this name already added");
                }
                page = new ShowAllBrandCommand().execute(request);
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
