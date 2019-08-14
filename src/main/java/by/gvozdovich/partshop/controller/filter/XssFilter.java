package by.gvozdovich.partshop.controller.filter;

import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * filter used to prevent xss attacks
 * @author Vadim Gvozdovich
 * @version 1.0
 */
@WebFilter(urlPatterns = {"/*"})
public class XssFilter implements Filter {
    private static Logger logger = LogManager.getLogger();
    private static final Set<String> BLACK_LIST_XSS = new HashSet<>(Arrays.asList(
            "<script", "</script>", "javascript:", "<", ">" ));

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String text = servletRequest.getParameter(parameterNames.nextElement()).toLowerCase();
            if(BLACK_LIST_XSS.contains(text)) {
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