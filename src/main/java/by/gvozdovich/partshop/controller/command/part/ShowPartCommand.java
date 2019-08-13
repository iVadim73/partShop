package by.gvozdovich.partshop.controller.command.part;

import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.tag.TagCommand;
import by.gvozdovich.partshop.controller.servlet.Router;
import by.gvozdovich.partshop.model.entity.Brand;
import by.gvozdovich.partshop.model.entity.Feedback;
import by.gvozdovich.partshop.model.entity.Part;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.BrandService;
import by.gvozdovich.partshop.model.service.FeedbackService;
import by.gvozdovich.partshop.model.service.PartService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * take and show Part from DB
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class ShowPartCommand implements Command {

    public ShowPartCommand() {
    }

    /**
     * @return String URI page that
     * forward to error page if an error happens
     * forward to page with result
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router page = new Router();

        try {
            String type = (String) request.getSession().getAttribute(CommandVarConstant.USER_TYPE);
            int partId = Integer.parseInt(request.getParameter(CommandVarConstant.PART_ID));
            Part part = PartService.getInstance().takePartById(partId);
            List<Feedback> feedbacks = FeedbackService.getInstance().takeAllFeedbackByPartId(partId);
            request.setAttribute(CommandVarConstant.FEEDBACKS, feedbacks);

            page = new TagCommand().execute(request);

            switch (type) {
                case CommandVarConstant.ADMIN:
                case CommandVarConstant.SELLER:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWPART);
                    List<Brand> brands = BrandService.getInstance().takeAllBrand();
                    request.setAttribute(CommandVarConstant.BRANDS, brands);
                    break;
                default:
                    page.setPage(CommandPathConstant.PATH_PAGE_SHOWPART_FOR_USER);
                    break;
            }

            request.setAttribute(CommandVarConstant.PART, part);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
        }

        return page;
    }
}
