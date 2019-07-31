package by.gvozdovich.partshop.controller.command;

import by.gvozdovich.partshop.controller.servlet.Router;
import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {

    public EmptyCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        // FIXME: 2019-06-29
        Router page = new Router();
        page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        return page;
    }
}
