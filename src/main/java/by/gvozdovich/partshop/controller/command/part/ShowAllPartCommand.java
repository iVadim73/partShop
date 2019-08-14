package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.PartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * take and show all Part from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ShowAllPartCommand implements Command {

    public ShowAllPartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to page with result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
            List<Part> parts;

            page = new TagCommand().execute(request);

            switch (type) {
                case CommandVarConstant.ADMIN:
                case CommandVarConstant.SELLER:
                    parts = PartService.getInstance().takeAllPart();
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART);
                    break;
                default:
                    parts = PartService.getInstance().takeAllPartForUser();
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART_FOR_USER);
                    break;
            }

            request.setAttribute(CommandVarConstant.PARTS, parts);
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
