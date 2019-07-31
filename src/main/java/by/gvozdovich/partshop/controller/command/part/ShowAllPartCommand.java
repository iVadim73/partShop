package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.controller.tag.DataToCustomTag;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.PartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllPartCommand implements Command {

    public ShowAllPartCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();

        String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
        List<Part> parts;

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

        DataToCustomTag dataToCustomTag = new DataToCustomTag(request);
        dataToCustomTag.makePageCount();

        return page;
    }
}
