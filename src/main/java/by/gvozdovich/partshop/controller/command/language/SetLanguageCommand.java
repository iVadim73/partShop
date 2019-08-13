package by.gvozdovich.partshop.controller.command.language;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import javax.servlet.http.HttpServletRequest;

/**
 * set language pages
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class SetLanguageCommand implements Command {

    public SetLanguageCommand() {
    }

    /**
     * @return String URI page that
     * forward to index page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();
        String language = request.getParameter(CommandVarConstant.LANGUAGE);
        request.getSession().setAttribute(CommandVarConstant.LANGUAGE, language);
        page.setRouterType(Router.RouterType.REDIRECT);
        page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
        return page;
    }
}
