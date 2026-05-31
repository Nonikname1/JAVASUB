package servlet;

import model.Group;
import service.GroupService;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ReportServlet extends HttpServlet {

    private GroupService groupService;
    private StudentService studentService;

    @Override
    public void init() {
        groupService   = (GroupService)   getServletContext().getAttribute("groupService");
        studentService = (StudentService) getServletContext().getAttribute("studentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("groups", groupService.getAllGroups());

        String param = req.getParameter("groupId");
        if (param != null && !param.isEmpty()) {
            int groupId = Integer.parseInt(param);
            Optional<Group> group = groupService.getGroupById(groupId);
            if (group.isPresent()) {
                req.setAttribute("selectedGroup", group.get());
                req.setAttribute("students", studentService.getStudentsByGroup(groupId));
            }
        }

        req.getRequestDispatcher("/WEB-INF/views/report.jsp").forward(req, resp);
    }
}
