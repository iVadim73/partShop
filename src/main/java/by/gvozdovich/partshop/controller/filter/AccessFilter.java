package by.gvozdovich.partshop.controller.filter;

import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandType;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * filter used to prevent access wrong user
 * @author Vadim Gvozdovich
 * @version 1.0
 */
@WebFilter( urlPatterns = {"/*"})
public class AccessFilter implements Filter {
    private static Logger logger = LogManager.getLogger();
    private static final String PICTURE_REG_EX = "^\\/picture\\/.*";
    private static final String BOOTSTRAP_REG_EX = "^\\/bootstrap\\/.*";
    private static final Set<String> WHITE_PATHS_FOR_GUEST = new HashSet<>(Arrays.asList(
            CommandPathConstant.PATH_PAGE_START, CommandPathConstant.PATH_PAGE_INDEX,
            CommandPathConstant.PATH_PAGE_ERROR, CommandPathConstant.PATH_PAGE_SIGNIN_SHORT,
            CommandPathConstant.PATH_PAGE_REGISTRATION_SHORT, CommandPathConstant.PATH_PAGE_FAVICON,
            CommandPathConstant.PATH_PAGE_PICTURE, CommandPathConstant.PATH_PAGE_SHOWALLPART_FOR_USER,
            CommandPathConstant.PATH_PAGE_SHOWPART_FOR_USER, CommandPathConstant.PATH_PAGE_MENU1,
            CommandPathConstant.PATH_PAGE_MENU2, CommandPathConstant.PATH_PAGE_MENU3,
            CommandPathConstant.PATH_PAGE_BOOTSTRAP));
    private static final Set<String> WHITE_PATHS_FOR_BUYER = new HashSet<>(Arrays.asList(
            CommandPathConstant.PATH_PAGE_SHOWALLCART, CommandPathConstant.PATH_PAGE_SHOWALLORDER_FOR_USER));
    private static final Set<String> WHITE_PATHS_FOR_SELLER = new HashSet<>(Arrays.asList(
            CommandPathConstant.PATH_PAGE_SHOWALLBRAND, CommandPathConstant.PATH_PAGE_SHOWALLORDER,
            CommandPathConstant.PATH_PAGE_SHOWALLPART, CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_SELLER,
            CommandPathConstant.PATH_PAGE_SHOWPART, CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_SELLER,
            CommandPathConstant.PATH_PAGE_UPLOAD_SERVLET));
    private static final Set<String> WHITE_COMMAND_FOR_GUEST = new HashSet<>(Arrays.asList(
            CommandType.ADD_TO_CART.name().toLowerCase(), CommandType.ADD_TO_WISH_LIST.name().toLowerCase(),
            CommandType.SEARCH_PART.name().toLowerCase(), CommandType.SET_LANGUAGE.name().toLowerCase(),
            CommandType.SIGNIN.name().toLowerCase(), CommandType.REGISTRATION.name().toLowerCase(),
            CommandType.SHOW_ALL_PART.name().toLowerCase(), CommandType.SHOW_PART.name().toLowerCase()));
    private static final Set<String> WHITE_COMMAND_FOR_BUYER = new HashSet<>(Arrays.asList(
            CommandType.SHOW_ALL_CART.name().toLowerCase(), CommandType.SHOW_USER.name().toLowerCase(),
            CommandType.SHOW_ALL_WISH_LIST.name().toLowerCase(), CommandType.SIGNOUT.name().toLowerCase(),
            CommandType.SHOW_ALL_ORDER.name().toLowerCase(), CommandType.SHOW_ALL_BILL.name().toLowerCase(),
            CommandType.DELETE_FROM_WISH_LIST.name().toLowerCase(), CommandType.UPDATE_CART.name().toLowerCase(),
            CommandType.DELETE_FROM_CART.name().toLowerCase(), CommandType.ADD_TO_ORDER.name().toLowerCase(),
            CommandType.UPDATE_PASSWORD.name().toLowerCase(), CommandType.UPDATE_USER_DATA.name().toLowerCase(),
            CommandType.ADD_FEEDBACK.name().toLowerCase()));
    private static final Set<String> WHITE_COMMAND_FOR_SELLER = new HashSet<>(Arrays.asList(
            CommandType.SHOW_ALL_CART.name().toLowerCase(), CommandType.SHOW_USER.name().toLowerCase(),
            CommandType.SHOW_ALL_WISH_LIST.name().toLowerCase(), CommandType.SIGNOUT.name().toLowerCase(),
            CommandType.SHOW_ALL_BILL.name().toLowerCase(), CommandType.ADD_BILL.name().toLowerCase(),
            CommandType.SHOW_ALL_BRAND.name().toLowerCase(), CommandType.SHOW_ALL_USER.name().toLowerCase(),
            CommandType.TO_UPDATE_BRAND_FORM.name().toLowerCase(), CommandType.ACTIVATE_DEACTIVATE_BRAND.name().toLowerCase(),
            CommandType.ADD_BRAND.name().toLowerCase(), CommandType.TO_ADD_PART_FORM.name().toLowerCase(),
            CommandType.TO_UPDATE_PART_FORM.name().toLowerCase(), CommandType.ACTIVATE_DEACTIVATE_PART.name().toLowerCase(),
            CommandType.ADD_PART.name().toLowerCase(), CommandType.SEARCH_USER.name().toLowerCase(),
            CommandType.SHOW_USER_FOR_SELLER_AND_ADMIN.name().toLowerCase(), CommandType.ACTIVATE_DEACTIVATE_USER.name().toLowerCase(),
            CommandType.TO_ADD_BRAND_FORM.name().toLowerCase(), CommandType.SEARCH_BRAND.name().toLowerCase(),
            CommandType.UPDATE_BRAND.name().toLowerCase(), CommandType.UPDATE_PART.name().toLowerCase(),
            CommandType.UPDATE_USER_DATA_FOR_SELLER.name().toLowerCase(), CommandType.DELETE_FEEDBACK.name().toLowerCase(),
            CommandType.TO_UPDATE_ORDER_FORM.name().toLowerCase()));

    @Override
    public void init(FilterConfig config) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;

        String path = String.valueOf(httpRequest.getRequestURI());
        String userType = (String) httpRequest.getSession().getAttribute(CommandVarConstant.USER_TYPE);

        if (path.matches(PICTURE_REG_EX)) {
            path = CommandPathConstant.PATH_PAGE_PICTURE;
        } else if (path.matches(BOOTSTRAP_REG_EX)) {
            path = CommandPathConstant.PATH_PAGE_BOOTSTRAP;
        }

        String command = req.getParameter(CommandVarConstant.COMMAND);
        switch (userType) {
            case CommandVarConstant.GUEST :
                if (WHITE_PATHS_FOR_GUEST.contains(path)) { // TODO: 2019-08-03 нужно ли объединять if и else if в один??? 
                    chain.doFilter(req, resp);
                } else if (CommandPathConstant.PATH_PAGE_CONTROLLER.equals(path) && WHITE_COMMAND_FOR_GUEST.contains(command)) {
                    chain.doFilter(req, resp);
                } else {
                    logger.error("Access fail for GUEST " + path + " " + command);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + CommandPathConstant.PATH_PAGE_SIGNIN_SHORT);
                }
                break;
            case CommandVarConstant.BUYER :
                if (WHITE_PATHS_FOR_GUEST.contains(path) || WHITE_PATHS_FOR_BUYER.contains(path)) {
                    chain.doFilter(req, resp);
                } else if (CommandPathConstant.PATH_PAGE_CONTROLLER.equals(path) &&
                        (WHITE_COMMAND_FOR_GUEST.contains(command) || WHITE_COMMAND_FOR_BUYER.contains(command))) {
                    chain.doFilter(req, resp);
                } else {
                    logger.error("Access fail for BUYER " + path + " " + command);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + CommandPathConstant.PATH_PAGE_SIGNIN_SHORT);
                }
                break;
            case CommandVarConstant.SELLER :
                if (WHITE_PATHS_FOR_GUEST.contains(path) || WHITE_PATHS_FOR_BUYER.contains(path)
                        || WHITE_PATHS_FOR_SELLER.contains(path)) {
                    chain.doFilter(req, resp);
                } else if (CommandPathConstant.PATH_PAGE_CONTROLLER.equals(path) &&
                        (WHITE_COMMAND_FOR_GUEST.contains(command) || WHITE_COMMAND_FOR_BUYER.contains(command)) ||
                            WHITE_COMMAND_FOR_SELLER.contains(command)) {
                    chain.doFilter(req, resp);
                } else {
                    logger.error("Access fail for SELLER " + path + " " + command);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + CommandPathConstant.PATH_PAGE_SIGNIN_SHORT);
                }
                break;
            case CommandVarConstant.ADMIN :
                chain.doFilter(req, resp);
                break;
            default:
                logger.error("Access fail" + path + " " + command);
                httpResponse.sendRedirect(httpRequest.getContextPath() + CommandPathConstant.PATH_PAGE_SIGNIN_SHORT);
                break;
        }
    }

    @Override
    public void destroy() {

    }
}
