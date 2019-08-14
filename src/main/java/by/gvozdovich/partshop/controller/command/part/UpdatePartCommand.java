package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.PartValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BrandService;
import by.gvozdovich.partshop.model.service.PartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * update Part on DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class UpdatePartCommand implements Command {

    public UpdatePartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to previous page
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            int partId = Integer.parseInt(request.getParameter(CommandVarConstant.PART_ID));
            int brandId = Integer.valueOf(request.getParameter(CommandVarConstant.BRAND_ID));
            Brand brand = BrandService.getInstance().takeBrandById(brandId);

            String info= null;
            try {
                info = new String(request.getParameter(CommandVarConstant.INFO).getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e);
            }

            String catalogNo = request.getParameter(CommandVarConstant.CATALOG_NO);
            String originalCatalogNo = request.getParameter(CommandVarConstant.ORIGINAL_CATALOG_NO);
            String strPrice = request.getParameter(CommandVarConstant.PRICE);
            strPrice = strPrice.replaceAll(",", ".");
            String pictureUrl = request.getParameter(CommandVarConstant.PICTURE_URL);
            String strWait = request.getParameter(CommandVarConstant.WAIT);
            String strStockCount = request.getParameter(CommandVarConstant.STOCK_COUNT);
            String active = request.getParameter(CommandVarConstant.ACTIVE);
            boolean isActive = active != null;

            PartValidator validator = new PartValidator();
            if (!validator.partValidate(catalogNo, originalCatalogNo, info, strPrice, strWait, strStockCount)) {
                logger.error("wrong data :" + catalogNo + " " + originalCatalogNo + " " + info + " " + strPrice
                        + " " + strWait + " " + strStockCount);
                page = goError(request, "wrong data");
            } else {
                BigDecimal price = new BigDecimal(strPrice);
                int wait = Integer.valueOf(strWait);
                int stockCount = Integer.valueOf(strStockCount);

                if (PartService.getInstance().update(partId, catalogNo, originalCatalogNo, info, price, pictureUrl, wait, brand, stockCount, isActive)) {
                    request.setAttribute(CommandVarConstant.CONDITION, "part updated successfully");
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "part updated error");
                }

                request.setAttribute(CommandVarConstant.PART_ID, partId);
                page = new ShowPartCommand().execute(request);
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
