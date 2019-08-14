package by.gvozdovich.partshop.controller.servlet;

import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.CommandVarConstant;
import by.gvozdovich.partshop.controller.command.part.ShowPartCommand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import by.gvozdovich.partshop.model.service.PartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;

/**
 * servlet used to upload media content
 * @author Vadim Gvozdovich
 * @version 1.0
 */
@WebServlet(name = "FileLoadServlet", urlPatterns = "/UploadServlet", loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileLoadServlet  extends HttpServlet {
    private static Logger logger = LogManager.getLogger();
    private static final String UPLOAD_DIR = "/users/iVadim73/IdeaProjects/finalTask/web";
    private static final String SHOW_DIR = "/picture/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = LogManager.getLogger();
        response.setContentType("text/html;charset=UTF-8");
        Part part = request.getPart("file");
        if (part != null && part.getSize() > 0) {
            String fileNameOld = part.getSubmittedFileName();
            String [] param = fileNameOld.split("\\.");

            String strPartId = request.getParameter(CommandVarConstant.PART_ID);
            int partId = Integer.parseInt(strPartId);
            by.gvozdovich.partshop.model.entity.Part currentPart;

            try {
                currentPart = PartService.getInstance().takePartById(partId);
                request.setAttribute(CommandVarConstant.PART, currentPart);

                String fileName = partId + "." + param[param.length - 1];
                String path = UPLOAD_DIR + SHOW_DIR + fileName;
                String pathFile = SHOW_DIR + fileName;

                part.write(path);
                PartService.getInstance().updatePicture(currentPart, pathFile);

                request.setAttribute(CommandVarConstant.CONDITION, "picture upload successfully");
                logger.info("picture upload successfully :" + pathFile);
            } catch (ServiceException e) {
                request.setAttribute(CommandVarConstant.CONDITION, "picture upload error");
                logger.error("picture upload error :" + fileNameOld);
            }

            request.setAttribute(CommandVarConstant.PART_ID, partId);
            new ShowPartCommand().execute(request);
            getServletContext().getRequestDispatcher(CommandPathConstant.PATH_PAGE_SHOWPART).forward(request, response);
        }
    }
}