package by.gvozdovich.partshop.controller.command.language;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;

public class SetLanguageCommand implements Command {

    public SetLanguageCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String language = request.getParameter(CommandVarConstant.LANGUAGE);
        request.getSession().setAttribute(CommandVarConstant.LANGUAGE, language);
        Router page = new Router();
        page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
        return page;
    }
}
