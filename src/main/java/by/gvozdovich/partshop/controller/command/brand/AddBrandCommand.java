package by.gvozdovich.partshop.controller.command.brand;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.validator.BrandValidator;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.BrandService;
import javax.servlet.http.HttpServletRequest;

public class AddBrandCommand implements Command {

    public AddBrandCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String name = request.getParameter(CommandVarConstant.NAME);
        String country = request.getParameter(CommandVarConstant.COUNTRY);
        String info = request.getParameter(CommandVarConstant.INFO);
        String active = request.getParameter(CommandVarConstant.ACTIVE);
        boolean isActive = active != null;

        BrandValidator validator = new BrandValidator();
        if (!(validator.nameValidate(name) && validator.countryValidate(country) && validator.infoValidate(info))) {
            throw new ServiceException("wrong data");
        }

        if (BrandService.getInstance().add(name, country, info, isActive)) {
            request.setAttribute(CommandVarConstant.CONDITION, "brand add completed successfully");
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "brand add error");
        }
        Router page = new ShowAllBrandCommand().execute(request);
        return page;
    }
}
