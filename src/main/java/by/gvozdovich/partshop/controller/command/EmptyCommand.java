package by.gvozdovich.partshop.controller.command;

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
        logger.error("empty command detect");
        return new Router();
    }
}
