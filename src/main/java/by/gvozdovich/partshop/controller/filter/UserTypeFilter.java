package by.gvozdovich.partshop.controller.filter;

import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * filter used to set guest type for unknown user
 * @author Vadim Gvozdovich
 * @version 1.0
 */
@WebFilter(urlPatterns = {"/*"})
public class UserTypeFilter implements Filter {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;

        String userType = (String) httpRequest.getSession().getAttribute(CommandVarConstant.USER_TYPE);

        if (userType == null) {
            httpRequest.getSession().setAttribute(CommandVarConstant.USER_TYPE, CommandVarConstant.GUEST);
            logger.info("User type was given: GUEST ");
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
