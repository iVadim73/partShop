package by.gvozdovich.partshop.controller.filter;

import by.gvozdovich.partshop.controller.command.CommandPathConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter( urlPatterns = {"/authorization/*", "/bill/*", "/brand/*", "/cart/*", "/common/*", "/order/*", "/part/*", "/user/*", "/wishlist/*"})
//@WebFilter( urlPatterns = {"/bill/*", "/brand/*", "/cart/*", "/common/*", "/order/*", "/part/*", "/user/*", "/wishlist/*"})
@WebFilter( urlPatterns = {"/wishlist/*"})

public class SecurityFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        req.setAttribute("pageCount", 1);
        httpResponse.sendRedirect(httpRequest.getContextPath() + CommandPathConstant.PATH_PAGE_INDEX);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
