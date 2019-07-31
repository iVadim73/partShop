package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BrandService;
import by.gvozdovich.partshop.model.logic.PartService;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public class ToUpdatePartFormCommand implements Command {

    public ToUpdatePartFormCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        Router page = new Router();

        List<Brand> brands = BrandService.getInstance().takeAllBrand();
        request.setAttribute(CommandVarConstant.BRANDS, brands);

        int partId = Integer.parseInt(request.getParameter(CommandVarConstant.PART_ID));
        Part part = PartService.getInstance().takePartById(partId);

        String catalogNo = part.getCatalogNo();
        String originalCatalogNo = part.getOriginalCatalogNo();
        String info = part.getInfo();
        BigDecimal price = part.getPrice();
        String pictureUrl = part.getPictureURL();
        int wait = part.getWait();
        Brand brand = part.getBrand();
        int stockCount = part.getStockCount();
        boolean isActive = part.getIsActive();

        request.setAttribute(CommandVarConstant.PART_ID, partId);
        request.setAttribute(CommandVarConstant.CATALOG_NO, catalogNo);
        request.setAttribute(CommandVarConstant.ORIGINAL_CATALOG_NO, originalCatalogNo);
        request.setAttribute(CommandVarConstant.INFO, info);
        request.setAttribute(CommandVarConstant.PRICE, price);
        request.setAttribute(CommandVarConstant.PICTURE_URL, pictureUrl);
        request.setAttribute(CommandVarConstant.WAIT, wait);
        request.setAttribute(CommandVarConstant.BRAND, brand);
        request.setAttribute(CommandVarConstant.STOCK_COUNT, stockCount);
        request.setAttribute(CommandVarConstant.IS_ACTIVE, isActive);

        page.setPage(CommandPathConstant.PATH_PAGE_UPDATEPART);
        return page;
    }
}
