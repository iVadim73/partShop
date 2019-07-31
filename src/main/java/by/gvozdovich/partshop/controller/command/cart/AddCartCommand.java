package by.gvozdovich.partshop.controller.command.cart;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.CartValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Cart;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.entity.User;
import by.gvozdovich.partshop.model.entity.WishList;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.CartService;
import by.gvozdovich.partshop.model.logic.PartService;
import by.gvozdovich.partshop.model.logic.UserService;
import by.gvozdovich.partshop.model.logic.WishListService;
import javax.servlet.http.HttpServletRequest;

public class AddCartCommand implements Command {

    public AddCartCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();
        String login = (String) request.getSession().getAttribute(CommandVarConstant.USER_LOGIN);

        int partId;
        int count;
        if (CommandPathConstant.PATH_PAGE_SIGNIN.equals(request.getSession().getAttribute(CommandVarConstant.LAST_PATH))) {
            partId = (int) request.getSession().getAttribute(CommandVarConstant.PART_ID);
            count = (int) request.getSession().getAttribute(CommandVarConstant.COUNT);
        } else {
            partId = Integer.valueOf(request.getParameter(CommandVarConstant.PART_ID));
            String strCount = request.getParameter(CommandVarConstant.COUNT);
            CartValidator validator = new CartValidator();
            if (!(validator.countValidate(strCount))) {
                throw new ServiceException("wrong data");
            }
            count = Integer.valueOf(strCount);
        }
        Part part = PartService.getInstance().takePartById(partId);

        if (login == null) {
            request.setAttribute(CommandVarConstant.CONDITION, "You have to sign in!");

            request.getSession().setAttribute(CommandVarConstant.LAST_PATH, CommandPathConstant.PATH_PAGE_SHOWALLPART);
            request.getSession().setAttribute(CommandVarConstant.ACTION, CommandVarConstant.ADD_CART);
            request.getSession().setAttribute(CommandVarConstant.PART_ID, partId);
            request.getSession().setAttribute(CommandVarConstant.COUNT, count);

            page.setPage(CommandPathConstant.PATH_PAGE_SIGNIN);
            return page;
        } else {
            request.getSession().removeAttribute(CommandVarConstant.LAST_PATH);
            request.getSession().removeAttribute(CommandVarConstant.PART_ID);
            request.getSession().removeAttribute(CommandVarConstant.ACTION);
            request.getSession().removeAttribute(CommandVarConstant.COUNT);
        }

        User user = UserService.getInstance().takeUserByLogin(login);
        String strWishListId = request.getParameter(CommandVarConstant.WISH_LIST_ID);

        try {
            Cart cart = CartService.getInstance().takeCartByPartIdAndUserId(partId, user.getUserId());
            int newCount = cart.getCount() + count;
            CartService.getInstance().update(cart.getCartId(), user, part, newCount);
            request.setAttribute(CommandVarConstant.CONDITION, "cart update completed successfully");
            deleteFromWishList(strWishListId);
        } catch (ServiceException e) {
            if (CartService.getInstance().add(user, part, count)) {
                request.setAttribute(CommandVarConstant.CONDITION, "cart add completed successfully");
                deleteFromWishList(strWishListId);
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "cart add error");
            }
        }

        page = new ShowAllCartCommand().execute(request);
        return page;
    }

    private void deleteFromWishList(String strWishListId) throws ServiceException {
        if (strWishListId != null) {
            int wishListId = Integer.valueOf(strWishListId);
            WishList wishList = WishListService.getInstance().takeWishListById(wishListId);
            WishListService.getInstance().delete(wishList);
        }
    }
}
