package by.gvozdovich.partshop.controller.command;

import by.gvozdovich.partshop.controller.servlet.Router;
import javax.servlet.http.HttpServletRequest;

/**
 * Basic Command interface.
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public interface Command {
    Router execute(HttpServletRequest request);

    default Router goError(HttpServletRequest request, String data) {
        Router page = new Router();
        request.setAttribute(CommandVarConstant.CONDITION, data);
        page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        return page;
    }
}
