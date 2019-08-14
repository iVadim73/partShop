package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.command.validator.PartValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.PartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * search Part from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class SearchPartCommand implements Command {

    public SearchPartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show all part page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String data = request.getParameter(CommandVarConstant.PART_OF_CATALOG_NO);
            String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);

            page = new TagCommand().execute(request);

            List<Part> parts;
            PartValidator validator = new PartValidator();
            if (data.isEmpty()) {
                page = new ShowAllPartCommand().execute(request);
            } else if (!validator.dataValidate(data)) {
                page = goError(request, "wrong data");
                logger.error("wrong data :" + data);
            } else {
                switch (type) {
                    case CommandVarConstant.ADMIN:
                    case CommandVarConstant.SELLER:
                        parts = PartService.getInstance().takePart(data);
                        page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART);
                        break;
                    default:
                        parts = PartService.getInstance().takePartForUser(data);
                        page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART_FOR_USER);
                        break;
                }
                request.setAttribute(CommandVarConstant.PARTS, parts);
                request.setAttribute(CommandVarConstant.PART_OF_CATALOG_NO, data);
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
