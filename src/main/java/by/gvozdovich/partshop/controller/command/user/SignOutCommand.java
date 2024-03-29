package by.gvozdovich.partshop.controller.command.user;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * sign out User
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class SignOutCommand implements Command {

    public SignOutCommand() {
    }

    /**
     * @return String URI page that
     * forward to index page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();
        HttpSession session = request.getSession();
        session.invalidate();
        page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
        return page;
    }
}
