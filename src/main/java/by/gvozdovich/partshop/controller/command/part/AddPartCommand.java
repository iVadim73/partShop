package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.PartValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BrandService;
import by.gvozdovich.partshop.model.logic.PartService;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class AddPartCommand implements Command {

    public AddPartCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        int brandId = Integer.valueOf(request.getParameter(CommandVarConstant.BRAND_ID));
        Brand brand = BrandService.getInstance().takeBrandById(brandId);

        String catalogNo = request.getParameter(CommandVarConstant.CATALOG_NO);
        String originalCatalogNo = request.getParameter(CommandVarConstant.ORIGINAL_CATALOG_NO);
        String info = request.getParameter(CommandVarConstant.INFO);
        String strPrice = request.getParameter(CommandVarConstant.PRICE);
        String pictureUrl = request.getParameter(CommandVarConstant.PICTURE_URL);
        String strWait = request.getParameter(CommandVarConstant.WAIT);
        String strStockCount = request.getParameter(CommandVarConstant.STOCK_COUNT);
        String active = request.getParameter(CommandVarConstant.ACTIVE);
        boolean isActive = active != null;

        PartValidator validator = new PartValidator();
        if(!(validator.catalogNoValidate(catalogNo) && validator.catalogNoValidate(originalCatalogNo)
                && validator.infoValidate(info) && validator.priceValidate(strPrice)
                && validator.waitValidate(strWait) && validator.stockCountValidate(strStockCount))) {
            throw new ServiceException("wrong data");
        }

        BigDecimal price = new BigDecimal(strPrice);
        int wait = Integer.valueOf(strWait);
        int stockCount = Integer.valueOf(strStockCount);

        if (PartService.getInstance().add(catalogNo, originalCatalogNo, info, price, pictureUrl, wait, brand, stockCount, isActive)) {
            request.setAttribute(CommandVarConstant.CONDITION, "part add completed successfully");
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "part add error");
        }
        Router page = new ShowAllPartCommand().execute(request);
        return page;
    }
}
