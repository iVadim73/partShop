package by.gvozdovich.partshop.controller.filter;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.brand.ShowAllBrandCommand;
import by.gvozdovich.partshop.controller.command.cart.ShowAllCartCommand;
import by.gvozdovich.partshop.controller.command.order.ShowAllOrderCommand;
import by.gvozdovich.partshop.controller.command.part.ShowAllPartCommand;
import by.gvozdovich.partshop.controller.command.part.ShowPartCommand;
import by.gvozdovich.partshop.controller.command.user.ShowAllUserCommand;
import by.gvozdovich.partshop.controller.command.user.ShowUserForSellerAndAdminCommand;
import by.gvozdovich.partshop.controller.command.validator.UserValidator;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter used to after redirect page
 * @author Vadim Gvozdovich
 * @version 1.0
 */
@WebFilter(urlPatterns = { CommandPathConstant.PATH_PAGE_SHOWALLBRAND, CommandPathConstant.PATH_PAGE_SHOWALLCART,
        CommandPathConstant.PATH_PAGE_SHOWALLORDER, CommandPathConstant.PATH_PAGE_SHOWALLORDER_FOR_USER,
        CommandPathConstant.PATH_PAGE_SHOWALLPART, CommandPathConstant.PATH_PAGE_SHOWALLPART_FOR_USER,
        CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_ADMIN, CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_SELLER,
        CommandPathConstant.PATH_PAGE_SHOWPART, CommandPathConstant.PATH_PAGE_SHOWPART_FOR_USER,
        CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_ADMIN, CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_SELLER})
public class UpdatePageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;

        String path = String.valueOf(httpRequest.getRequestURI());
        Command command;

        switch (path) {
            case CommandPathConstant.PATH_PAGE_SHOWALLBRAND:
                new ShowAllBrandCommand().execute(httpRequest);
                break;
            case CommandPathConstant.PATH_PAGE_SHOWALLCART:
                new ShowAllCartCommand().execute(httpRequest);
                break;
            case CommandPathConstant.PATH_PAGE_SHOWALLORDER:
            case CommandPathConstant.PATH_PAGE_SHOWALLORDER_FOR_USER:
                command = new ShowAllOrderCommand();
                setUserIdAndData(httpRequest,httpResponse, command);
                break;
            case CommandPathConstant.PATH_PAGE_SHOWALLPART:
            case CommandPathConstant.PATH_PAGE_SHOWALLPART_FOR_USER:
                new ShowAllPartCommand().execute(httpRequest);
                break;
            case CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_ADMIN:
            case CommandPathConstant.PATH_PAGE_SHOWALLUSER_FOR_SELLER:
                new ShowAllUserCommand().execute(httpRequest);
                break;
            case CommandPathConstant.PATH_PAGE_SHOWPART:
            case CommandPathConstant.PATH_PAGE_SHOWPART_FOR_USER:
                new ShowPartCommand().execute(httpRequest);
                break;
            case CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_ADMIN:
            case CommandPathConstant.PATH_PAGE_SHOWUSER_FOR_SELLER:
                command = new ShowUserForSellerAndAdminCommand();
                setUserIdAndData(httpRequest,httpResponse, command);
                break;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }

    private void setUserIdAndData(HttpServletRequest req, HttpServletResponse resp, Command command) throws IOException {
        String strUserId = req.getParameter(CommandVarConstant.USER_ID);
        UserValidator validator = new UserValidator();
        if (validator.idValidate(strUserId)) {
            int userId = Integer.parseInt(strUserId);
            req.setAttribute(CommandVarConstant.USER_ID, userId);
            command.execute(req);
        } else {
            resp.sendRedirect(req.getContextPath() + CommandPathConstant.PATH_PAGE_ERROR);
        }
    }
}
