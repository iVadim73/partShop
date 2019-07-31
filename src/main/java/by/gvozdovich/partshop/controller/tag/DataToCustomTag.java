package by.gvozdovich.partshop.controller.tag;

import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import javax.servlet.http.HttpServletRequest;

public class DataToCustomTag { // FIXME: 2019-07-27 тут????
    private HttpServletRequest request;

    public DataToCustomTag(HttpServletRequest request) {
        this.request = request;
//        request.removeAttribute(CommandVarConstant.PAGE_COUNT);
//        request.removeAttribute(CommandVarConstant.NEXT_PAGE);
    }

    public void makePageCount() {
        String strPageCount = request.getParameter(CommandVarConstant.PAGE_COUNT);
        String strNextPage = request.getParameter(CommandVarConstant.NEXT_PAGE);

        int pageCount;
        if (strPageCount == null) {
            pageCount = 1;
        } else {
            pageCount = Integer.valueOf(strPageCount);
        }

        int nextPage = 0;
        if (strNextPage != null) {
            nextPage = Integer.valueOf(strNextPage);
        }
        request.setAttribute(CommandVarConstant.PAGE_COUNT, pageCount + nextPage);
    }
}
