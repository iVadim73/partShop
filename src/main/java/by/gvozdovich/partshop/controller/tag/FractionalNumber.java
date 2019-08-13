package by.gvozdovich.partshop.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * custom jstl tag for formatting digits
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class FractionalNumber extends TagSupport {

    private BigDecimal number;

    @Override
    public int doStartTag() throws JspException {

        JspWriter writer = pageContext.getOut();

        NumberFormat formatter = new DecimalFormat("#0.00");

        try {
            writer.write(formatter.format(number));
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
