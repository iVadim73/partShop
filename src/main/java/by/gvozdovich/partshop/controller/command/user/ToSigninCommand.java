package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import javax.servlet.http.HttpServletRequest;

/**
 * forward to signin page
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ToSigninCommand implements Command {

    public ToSigninCommand() {
    }

    /**
     * @return String URI page that
     * forward to signin page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();
        page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
        return page;
    }
}
