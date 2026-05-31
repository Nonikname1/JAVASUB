package servlet;

import db.ConnectionFactory;
import db.SchemaInitializer;
import repository.GroupRepository;
import repository.StudentRepository;
import repository.impl.JdbcGroupRepository;
import repository.impl.JdbcStudentRepository;
import service.GroupService;
import service.StudentService;
import service.impl.GroupServiceImpl;
import service.impl.StudentServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        String configPath = ctx.getRealPath("/WEB-INF/db.properties");
        ConnectionFactory.setConfigPath(configPath);

        SchemaInitializer.init();

        GroupRepository groupRepo = new JdbcGroupRepository();
        StudentRepository studentRepo = new JdbcStudentRepository();

        GroupService groupService = new GroupServiceImpl(groupRepo, studentRepo);
        StudentService studentService = new StudentServiceImpl(studentRepo, groupRepo);

        ctx.setAttribute("groupService", groupService);
        ctx.setAttribute("studentService", studentService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
