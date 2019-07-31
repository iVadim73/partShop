package by.gvozdovich.partshop.controller.servlet;

import by.gvozdovich.partshop.controller.command.ActionFactory;
import by.gvozdovich.partshop.controller.command.Command;
import by.gvozdovich.partshop.controller.command.CommandPathConstant;
import by.gvozdovich.partshop.controller.command.EmptyCommand;
import by.gvozdovich.partshop.model.exception.ServiceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet({"/controller"})
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
        Optional<Command> optionalCommand = ActionFactory.defineCommand(req.getParameter("command"));
        Command command = optionalCommand.orElse(new EmptyCommand());
        Router page = new Router();
        try {
            page = command.execute(req);
        } catch (ServiceException e) {
            page.setPage(CommandPathConstant.PATH_PAGE_ERROR);
            throw new ServletException("Process request fail", e); // FIXME: 2019-07-26 бросать ошибку???
        }

        if(page != null) {
            if (page.getRouterType().equals(Router.RouterType.REDIRECT)) {
                resp.sendRedirect(page.getPage());
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(page.getPage());
                requestDispatcher.forward(req, resp);
            }
        } else {
            page.setPage(CommandPathConstant.PATH_PAGE_INDEX);
        }
    }
}
