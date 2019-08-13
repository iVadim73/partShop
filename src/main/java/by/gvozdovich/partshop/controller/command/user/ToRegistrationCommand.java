package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import javax.servlet.http.HttpServletRequest;

/**
 * forward to registration page
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ToRegistrationCommand implements Command {

    public ToRegistrationCommand() {
    }

    /**
     * @return String URI page that
     * forward to registration page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();
        page.setPage(CommandPathConstant.PATH_PAGE_REGISTRATION);
        return page;
    }
}
