package by.gvozdovich.partshop.controller.filter;

import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Enumeration;

/**
 * filter used to prevent xss attacks
 * @author Vadim Gvozdovich
 * @version 1.0
 */
@WebFilter(urlPatterns = {"/*"})
public class XssFilter implements Filter {
    private static Logger logger = LogManager.getLogger();
    private static final String XSS_SCRIPT_TAG = "<script";
    private static final String XSS_SCRIPT_END_TAG = "</script>";
    private static final String JAVASCRIPT = "javascript:";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String text = servletRequest.getParameter(parameterNames.nextElement()).toLowerCase();
            if(text.contains(XSS_SCRIPT_TAG) || text.contains(XSS_SCRIPT_END_TAG) || text.contains(JAVASCRIPT)){
                logger.warn("xss detected");
                RequestDispatcher dispatcher = servletRequest.getServletContext().
                        getRequestDispatcher(CommandPathConstant.PATH_PAGE_ERROR);
                dispatcher.forward(servletRequest, servletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}