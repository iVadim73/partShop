package by.gvozdovich.partshop.controller.command;

import by.gvozdovich.partshop.controller.command.bill.AddBillCommand;
import by.gvozdovich.partshop.controller.command.bill.ShowAllBillCommand;
import by.gvozdovich.partshop.controller.command.cart.AddCartCommand;
import by.gvozdovich.partshop.controller.command.cart.DeleteFromCartCommand;
import by.gvozdovich.partshop.controller.command.cart.ShowAllCartCommand;
import by.gvozdovich.partshop.controller.command.cart.UpdateCartCommand;
import by.gvozdovich.partshop.controller.command.brand.*;
import by.gvozdovich.partshop.controller.command.feedback.AddFeedbackCommand;
import by.gvozdovich.partshop.controller.command.feedback.DeleteFeedbackCommand;
import by.gvozdovich.partshop.controller.command.language.SetLanguageCommand;
import by.gvozdovich.partshop.controller.command.order.*;
import by.gvozdovich.partshop.controller.command.part.*;
import by.gvozdovich.partshop.controller.command.user.*;
import by.gvozdovich.partshop.controller.command.wishlist.AddWishListCommand;
import by.gvozdovich.partshop.controller.command.wishlist.DeleteFromWishListCommand;
import by.gvozdovich.partshop.controller.command.wishlist.ShowAllWishListCommand;

/**
 * All types of application {@link Command}
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public enum CommandType {
    REGISTRATION(new RegistrationCommand()),
    SIGNIN(new SignInCommand()),
    SIGNOUT(new SignOutCommand()),
    SHOW_ALL_BRAND(new ShowAllBrandCommand()),
    ADD_BRAND(new AddBrandCommand()),
    UPDATE_BRAND(new UpdateBrandCommand()),
    ACTIVATE_DEACTIVATE_BRAND(new ActivateDeactivateBrandCommand()),
    SEARCH_BRAND(new SearchBrandCommand()),
    TO_UPDATE_BRAND_FORM(new ToUpdateBrandFormCommand()),
    SHOW_ALL_PART(new ShowAllPartCommand()),
    ADD_PART(new AddPartCommand()),
    UPDATE_PART(new UpdatePartCommand()),
    ACTIVATE_DEACTIVATE_PART(new ActivateDeactivatePartCommand()),
    SEARCH_PART(new SearchPartCommand()),
    TO_UPDATE_PART_FORM(new ToUpdatePartFormCommand()),
    TO_ADD_PART_FORM(new ToAddPartFormCommand()),
    ADD_TO_WISH_LIST(new AddWishListCommand()),
    DELETE_FROM_WISH_LIST(new DeleteFromWishListCommand()),
    SHOW_ALL_WISH_LIST(new ShowAllWishListCommand()),
    ADD_TO_CART(new AddCartCommand()),
    DELETE_FROM_CART(new DeleteFromCartCommand()),
    SHOW_ALL_CART(new ShowAllCartCommand()),
    UPDATE_CART(new UpdateCartCommand()),
    SET_LANGUAGE(new SetLanguageCommand()),
    SHOW_ALL_ORDER(new ShowAllOrderCommand()),
    SHOW_ALL_BILL(new ShowAllBillCommand()),
    SHOW_USER(new ShowUserCommand()),
    UPDATE_PASSWORD(new UpdatePasswordCommand()),
    UPDATE_USER_DATA(new UpdateUserDataCommand()),
    ADD_TO_ORDER(new AddOrderCommand()),
    SEARCH_USER(new SearchUserCommand()),
    SHOW_ALL_USER(new ShowAllUserCommand()),
    SHOW_USER_FOR_SELLER_AND_ADMIN(new ShowUserForSellerAndAdminCommand()),
    ACTIVATE_DEACTIVATE_USER(new ActivateDeactivateUserCommand()),
    UPDATE_USER_DATA_FOR_ADMIN(new UpdateUserDataForAdminCommand()),
    UPDATE_USER_DATA_FOR_SELLER(new UpdateUserDataForSellerCommand()),
    ADD_BILL(new AddBillCommand()),
    TO_REGISTRATION(new ToRegistrationCommand()),
    TO_SIGNIN(new ToSigninCommand()),
    TO_ADD_BRAND_FORM(new ToAddBrandFormCommand()),
    ADD_ALL_TO_ORDER(new AddAllOrderCommand()),
    TO_UPDATE_ORDER_FORM(new ToUpdateOrderFormCommand()),
    UPDATE_ORDER(new UpdateOrderCommand()),
    SHOW_PART(new ShowPartCommand()),
    SEARCH_ORDER(new SearchOrderCommand()),
    ADD_FEEDBACK(new AddFeedbackCommand()),
    DELETE_FEEDBACK(new DeleteFeedbackCommand()),

    ;

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
