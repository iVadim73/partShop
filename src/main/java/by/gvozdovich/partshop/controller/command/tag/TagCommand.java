package by.gvozdovich.partshop.controller.command.tag;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import javax.servlet.http.HttpServletRequest;

/**
 * set parameter for custom tag working
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class TagCommand implements Command {
    public TagCommand() {

    }

    /**
     * @return empty page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        String strPageCount = request.getParameter(CommandVarConstant.PAGE_COUNT);
        String strNextPage = request.getParameter(CommandVarConstant.NEXT_PAGE);

        int pageCount;
        if (strPageCount == null) {
            pageCount = 1;
        } else {
            pageCount = Integer.valueOf(strPageCount);
        }

        int nextPage = 0;
        if (strNextPage != null) {
            nextPage = Integer.valueOf(strNextPage);
        }
        request.setAttribute(CommandVarConstant.PAGE_COUNT, pageCount + nextPage);

        return new Router();
    }

}
