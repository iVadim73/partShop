package by.gvozdovich.partshop.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * custom jstl tag for pagination
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class PageListTag extends TagSupport {
    private int pageCount;
    private int elementCount;
    private String command;
    private String login;

    @Override
    public int doStartTag() throws JspException {

        JspWriter writer = pageContext.getOut();

        try {
            if (!(pageCount == 1 && elementCount < 11)) {
                if (pageCount == 1) {
                    writer.write("<form method=\"post\" action=\"/controller\">\n" +
                            "            <input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"pageCount\" value=\"" + pageCount + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"login\" value=\"" + login + "\"/>\n" +
                            "            <button>" + pageCount + "</button>\n" +
                            "            <button type=\"submit\" name=\"nextPage\" value=\"1\">--></button>\n" +
                            "        </form>");
                } else if (elementCount < 11) {
                    writer.write("<form method=\"post\" action=\"/controller\">\n" +
                            "            <input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"pageCount\" value=\"" + pageCount + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"login\" value=\"" + login + "\"/>\n" +
                            "            <button type=\"submit\" name=\"nextPage\" value=\"-1\"><--</button>\n" +
                            "            <button>" + pageCount + "</button>\n" +
                            "        </form>");
                } else {
                    writer.write("<form method=\"post\" action=\"/controller\">\n" +
                            "            <input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"pageCount\" value=\"" + pageCount + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"login\" value=\"" + login + "\"/>\n" +
                            "            <button type=\"submit\" name=\"nextPage\" value=\"-1\"><--</button>\n" +
                            "            <button>" + pageCount + "</button>\n" +
                            "            <button type=\"submit\" name=\"nextPage\" value=\"1\">--></button>\n" +
                            "        </form>");
                }
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setElementCount(int elementCount) {
        this.elementCount = elementCount;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
