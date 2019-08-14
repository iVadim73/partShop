package by.gvozdovich.partshop.controller.command;

import by.gvozdovich.partshop.controller.command.user.ToRegistrationCommand;
import by.gvozdovich.partshop.controller.command.user.ToSigninCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * use for protection, when {@link CommandType} == null
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class EmptyCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    public EmptyCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        logger.warn("empty command detect");
        Router page;
        String path = String.valueOf(request.getRequestURI());
        switch (path) {
            case CommandPathConstant.PATH_PAGE_SIGNIN_SHORT:
                page = new ToSigninCommand().execute(request);
                break;
            case CommandPathConstant.PATH_PAGE_REGISTRATION_SHORT:
                page = new ToRegistrationCommand().execute(request);
                break;
            default:
                page = new Router();
                page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
                break;
        }

        return page;
    }
}
