package servlet;

import model.Group;
import model.Student;
import service.GroupService;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class StudentServlet extends HttpServlet {

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

        req.getRequestDispatcher("/WEB-INF/views/students.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action  = req.getParameter("action");
        int    groupId = Integer.parseInt(req.getParameter("groupId"));

        if ("add".equals(action)) {
            String name = req.getParameter("name");
            if (name != null && !name.trim().isEmpty()) {
                studentService.createStudent(name.trim(), groupId);
            }
        } else if ("delete".equals(action)) {
            studentService.deleteStudent(Integer.parseInt(req.getParameter("id")));
        } else if ("updateTask".equals(action)) {
            int     studentId = Integer.parseInt(req.getParameter("studentId"));
            int     taskIndex = Integer.parseInt(req.getParameter("taskIndex"));
            boolean status    = "true".equals(req.getParameter("status"));
            studentService.updateTaskStatus(studentId, taskIndex, status);
        }

        resp.sendRedirect(req.getContextPath() + "/students?groupId=" + groupId);
    }
}
