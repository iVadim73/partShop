package by.gvozdovich.partshop.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * filter used to set encoding
 * @author Vadim Gvozdovich
 * @version 1.0
 */
@WebFilter(urlPatterns = { "/*" },
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param") })
public class EncodingFilter implements Filter {
    private String code;

    @Override
    public void init(FilterConfig fConfig) {
        code = fConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String codeRequest = req.getCharacterEncoding();

        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            req.setCharacterEncoding(code);
            resp.setCharacterEncoding(code);
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
