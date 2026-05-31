package servlet;

import model.Group;
import service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupServlet extends HttpServlet {

    private GroupService groupService;

    @Override
    public void init() {
        groupService = (GroupService) getServletContext().getAttribute("groupService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("groups", groupService.getAllGroups());
        req.getRequestDispatcher("/WEB-INF/views/groups.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            String name = req.getParameter("name");
            if (name != null && !name.trim().isEmpty()) {
                groupService.createGroup(name.trim());
            }
        } else if ("delete".equals(action)) {
            groupService.deleteGroup(Integer.parseInt(req.getParameter("id")));
        }

        resp.sendRedirect(req.getContextPath() + "/groups");
    }
}
