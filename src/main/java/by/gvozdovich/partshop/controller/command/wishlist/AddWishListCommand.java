package by.gvozdovich.partshop.controller.command.wishlist;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.PartService;
import by.gvozdovich.partshop.model.service.UserService;
import by.gvozdovich.partshop.model.service.WishListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * add WishList to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddWishListCommand implements Command {

    public AddWishListCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to sign in page if user == null
     * forward to show all wish list page if added
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            String login = (String) request.getSession().getAttribute(CommandVarConstant.CURRENT_LOGIN);
            int partId;

            if (CommandPathConstant.PATH_PAGE_SIGNIN.equals(request.getSession().getAttribute(CommandVarConstant.LAST_PATH))) {
                partId = (int) request.getSession().getAttribute(CommandVarConstant.PART_ID);
            } else {
                partId = Integer.valueOf(request.getParameter(CommandVarConstant.PART_ID));
            }

            Part part = PartService.getInstance().takePartById(partId);

            if (login == null) {
                request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");
                request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_SHOWALLPART);
                request.getSession().setAttribute(CommandVarConstant.ACTION, CommandVarConstant.ADD_WISH_LIST);
                request.getSession().setAttribute(CommandVarConstant.PART_ID, partId);

                page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            } else {
                request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
                request.getSession().removeAttribute(CommandVarConstant.ACTION);
                request.getSession().removeAttribute(CommandVarConstant.PART_ID);

                User user = UserService.getInstance().takeUserByLogin(login);

                if (WishListService.getInstance().add(user, part)) {
                    request.setAttribute(CommandVarConstant.CONDITION, "wish list add completed successfully");
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "wish list already added");
                }
                page = new ShowAllWishListCommand().execute(request);
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
