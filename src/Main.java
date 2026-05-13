import repository.GroupRepository;
import repository.StudentRepository;
import repository.impl.InMemoryGroupRepository;
import repository.impl.InMemoryStudentRepository;
import service.GroupService;
import service.StudentService;
import service.impl.GroupServiceImpl;
import service.impl.StudentServiceImpl;
import ui.ConsoleReader;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        GroupRepository groupRepository = new InMemoryGroupRepository();
        StudentRepository studentRepository = new InMemoryStudentRepository();

        GroupService groupService = new GroupServiceImpl(groupRepository, studentRepository);
        StudentService studentService = new StudentServiceImpl(studentRepository, groupRepository);

        ConsoleReader reader = new ConsoleReader();
        ConsoleUI ui = new ConsoleUI(groupService, studentService, reader);

        ui.run();
        reader.close();
    }
}
