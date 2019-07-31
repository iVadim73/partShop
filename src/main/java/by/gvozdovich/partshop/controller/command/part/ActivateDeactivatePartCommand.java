package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.logic.PartService;
import javax.servlet.http.HttpServletRequest;

public class ActivateDeactivatePartCommand implements Command {

    public ActivateDeactivatePartCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        int partId = Integer.parseInt(request.getParameter(CommandVarConstant.PART_ID));
        Part part = PartService.getInstance().takePartById(partId);
        boolean isActive = part.getIsActive();

        if (PartService.getInstance().activateDeactivate(part)) {
            if(isActive) {
                request.setAttribute(CommandVarConstant.CONDITION, "part deactivated successfully");
            } else {
                request.setAttribute(CommandVarConstant.CONDITION, "part activated successfully");
            }
        } else {
            request.setAttribute(CommandVarConstant.CONDITION, "part activated error");
        }
        Router page = new ShowAllPartCommand().execute(request);
        return page;
    }
}
