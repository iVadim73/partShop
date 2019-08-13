package by.gvozdovich.partshop.controller.servlet;

import by.gvozdovich.partshop.controller.command.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * main servlet
 * @author Vadim Gvozdovich
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/"})
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Command> optionalCommand = CommandFactory.defineCommand(req.getParameter(CommandVarConstant.COMMAND));
        Command command = optionalCommand.orElse(new EmptyCommand());
        Router page = command.execute(req);

        if(page != null) {
            if (page.getRouterType().equals(Router.RouterType.REDIRECT)) {
                resp.sendRedirect(page.getPage());
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(page.getPage());
                requestDispatcher.forward(req, resp);
            }
        } else {
            page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
            resp.sendRedirect(page.getPage());
        }
    }
}
