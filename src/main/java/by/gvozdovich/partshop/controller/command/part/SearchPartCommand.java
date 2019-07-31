package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.PartValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.PartService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SearchPartCommand implements Command {

    public SearchPartCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String data = request.getParameter(CommandVarConstant.PART_OF_CATALOG_NO);
        String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);

        List<Part> parts;
        PartValidator validator = new PartValidator();
        if (data.isEmpty()) {
            page = new ShowAllPartCommand().execute(request);
//            switch (type) {
//                case CommandVarConstant.ADMIN:
//                case CommandVarConstant.SELLER:
//                    parts = PartService.getInstance().takeAllPart();
//                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART);
//                    break;
//                default:
//                    parts = PartService.getInstance().takeAllPartForUser();
//                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART_FOR_USER);
//                    break;
//            }
        } else if (!validator.dataValidate(data)) {
            throw new ServiceException("wrong data");
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

            DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
            dataToCustomTag.makePageCount();
        }

        //page.setPage(CommandPathConstant.PATH_PAGE_SHOWALLPART);
        return page;
    }
}
