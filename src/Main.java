import db.SchemaInitializer;
import repository.GroupRepository;
import repository.StudentRepository;
import repository.impl.JdbcGroupRepository;
import repository.impl.JdbcStudentRepository;
import service.GroupService;
import service.StudentService;
import service.impl.GroupServiceImpl;
import service.impl.StudentServiceImpl;
import ui.ConsoleReader;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        SchemaInitializer.init();

        GroupRepository groupRepository = new JdbcGroupRepository();
        StudentRepository studentRepository = new JdbcStudentRepository();

        GroupService groupService = new GroupServiceImpl(groupRepository, studentRepository);
        StudentService studentService = new StudentServiceImpl(studentRepository, groupRepository);

        ConsoleReader reader = new ConsoleReader();
        ConsoleUI ui = new ConsoleUI(groupService, studentService, reader);

        ui.run();
        reader.close();
    }
}
