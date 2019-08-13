package by.gvozdovich.partshop.controller.command.brand;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import javax.servlet.http.HttpServletRequest;

/**
 * forward to add brand page
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ToAddBrandFormCommand implements Command {

    public ToAddBrandFormCommand() {
    }

    /**
     * @return String URI page that
     * forward to add brand page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();
        page.setPage(CommandPathConstant.PATH_PAGE_ADDBRAND);
        return page;
    }
}
