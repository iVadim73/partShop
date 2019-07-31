package by.gvozdovich.partshop.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PageListTag extends TagSupport {
    private int pageCount;
    private int elementCount;
    private String command;

    @Override
    public int doStartTag() throws JspException {

        JspWriter writer = pageContext.getOut();
        double maxPage = (double) elementCount / 10;
        int lastPage = (int) Math.ceil(maxPage);

        try {
            if (elementCount == 0) {
                writer.write("no data");
            } else if (lastPage != 1) {
                if (pageCount == 1) {
                    writer.write("<form method=\"post\" action=\"/controller\">\n" +
                            "            <input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"pageCount\" value=\"" + pageCount + "\"/>\n" +
                            pageCount +
                            "            <button type=\"submit\" name=\"nextPage\" value=\"1\">--></button>\n" +
                            "        </form>");
                } else if (pageCount == lastPage) {
                    writer.write("<form method=\"post\" action=\"/controller\">\n" +
                            "            <input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"pageCount\" value=\"" + pageCount + "\"/>\n" +
                            "            <button type=\"submit\" name=\"nextPage\" value=\"-1\"><--</button>\n" +
                            pageCount +
                            "        </form>");
                } else {
                    writer.write("<form method=\"post\" action=\"/controller\">\n" +
                            "            <input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>\n" +
                            "            <input type=\"hidden\" name=\"pageCount\" value=\"" + pageCount + "\"/>\n" +
                            "            <button type=\"submit\" name=\"nextPage\" value=\"-1\"><--</button>\n" +
                            pageCount +
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
}
