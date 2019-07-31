package by.gvozdovich.partshop.controller.filter;

import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String userType = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute(CommandVarConstant.USER_TYPE);

        if (userType == null) {
            ((HttpServletRequest) servletRequest).getSession().setAttribute(CommandVarConstant.USER_TYPE, CommandVarConstant.GUEST);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
