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
import java.math.BigDecimal;

/**
 * add Part to DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class AddPartCommand implements Command {

    public AddPartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to show all part page if added
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Logger logger = LogManager.getLogger();
        Router page = new Router();

        try {
            int brandId = Integer.valueOf(request.getParameter(CommandVarConstant.BRAND_ID));
            Brand brand = BrandService.getInstance().takeBrandById(brandId);

            String catalogNo = request.getParameter(CommandVarConstant.CATALOG_NO);
            String info = request.getParameter(CommandVarConstant.INFO);
            String originalCatalogNo = request.getParameter(CommandVarConstant.ORIGINAL_CATALOG_NO);
            String strPrice = request.getParameter(CommandVarConstant.PRICE);
            strPrice = strPrice.replaceAll(",", ".");
            String pictureUrl = request.getParameter(CommandVarConstant.PICTURE_URL);
            String strStockCount = request.getParameter(CommandVarConstant.STOCK_COUNT);
            String strWait = request.getParameter(CommandVarConstant.WAIT);
            String active = request.getParameter(CommandVarConstant.ACTIVE);
            boolean isActive = active != null;

            PartValidator validator = new PartValidator();
            if (!validator.partValidate(catalogNo, originalCatalogNo, info, strPrice, strWait, strStockCount)) {
                page = goError(request, "wrong data");
                logger.error("wrong data :" + catalogNo + " " + originalCatalogNo + " " + info + " " + strPrice
                        + " " + strWait + " " + strStockCount);
            } else {
                BigDecimal price = new BigDecimal(strPrice);
                int wait = Integer.valueOf(strWait);
                int stockCount = Integer.valueOf(strStockCount);

                int partId = PartService.getInstance().add(catalogNo, originalCatalogNo, info, price, pictureUrl, wait, brand, stockCount, isActive);
                if (partId > 0) {
                    request.setAttribute(CommandVarConstant.CONDITION, "part add completed successfully");

                    String userType = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
                    page.setRouterType(Router.RouterType.REDIRECT);
                    String pageToRedirect;

                    switch (userType) {
                        case CommandVarConstant.ADMIN:
                        case CommandVarConstant.SELLER:
                            pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWPART + "?partId=" + partId;
                            page.setPage(pageToRedirect);
                            break;
                        case CommandVarConstant.BUYER:
                            pageToRedirect = CommandPathConstant.PATH_PAGE_SHOWPART_FOR_USER + "?partId=" + partId;
                            page.setPage(pageToRedirect);
                            break;
                        default:
                            page = goError(request, "access fail");
                            break;
                    }
                } else {
                    request.setAttribute(CommandVarConstant.CONDITION, "part with this catalogNo and this brand already added");
                }
            }
        } catch (ServiceException e) {
            logger.error("exception in Service layer :" + e);
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
