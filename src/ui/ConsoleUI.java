package ui;

import model.Group;
import model.Student;
import service.GroupService;
import service.StudentService;

import java.util.List;
import java.util.Optional;

public class ConsoleUI {
    private final GroupService groupService;
    private final StudentService studentService;
    private final ConsoleReader reader;

    public ConsoleUI(GroupService groupService, StudentService studentService, ConsoleReader reader) {
        this.groupService = groupService;
        this.studentService = studentService;
        this.reader = reader;
    }

    public void run() {
        System.out.println("=== Учет задач по Информатике ===");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Главное меню ---");
            System.out.println("1. Управление группами");
            System.out.println("2. Управление студентами");
            System.out.println("3. Отчёт по группе");
            System.out.println("0. Выход");
            switch (reader.readInt("Выбор: ")) {
                case 1: handleGroups();   break;
                case 2: handleStudents(); break;
                case 3: handleReport();   break;
                case 0: running = false;  break;
                default: System.out.println("Неверный выбор.");
            }
        }
        System.out.println("До свидания!");
    }

    // ── Группы ──────────────────────────────────────────────────────────────

    private void handleGroups() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Группы ---");
            System.out.println("1. Показать все группы");
            System.out.println("2. Создать группу");
            System.out.println("3. Удалить группу");
            System.out.println("0. Назад");
            switch (reader.readInt("Выбор: ")) {
                case 1: showAllGroups(); break;
                case 2: createGroup();   break;
                case 3: deleteGroup();   break;
                case 0: back = true;     break;
                default: System.out.println("Неверный выбор.");
            }
        }
    }

    private void showAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        if (groups.isEmpty()) {
            System.out.println("Групп нет.");
            return;
        }
        System.out.println("\nСписок групп:");
        for (Group g : groups) {
            System.out.println("  " + g);
        }
    }

    private void createGroup() {
        String name = reader.readLine("Название группы: ");
        if (name.isEmpty()) { System.out.println("Название не может быть пустым."); return; }
        Group group = groupService.createGroup(name);
        System.out.println("Группа создана: " + group);
    }

    private void deleteGroup() {
        showAllGroups();
        int id = reader.readInt("ID группы для удаления: ");
        if (!groupService.getGroupById(id).isPresent()) {
            System.out.println("Группа не найдена.");
            return;
        }
        groupService.deleteGroup(id);
        System.out.println("Группа и все её студенты удалены.");
    }

    // ── Студенты ─────────────────────────────────────────────────────────────

    private void handleStudents() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Студенты ---");
            System.out.println("1. Показать студентов группы");
            System.out.println("2. Добавить студента");
            System.out.println("3. Удалить студента");
            System.out.println("4. Обновить статус задачи");
            System.out.println("0. Назад");
            switch (reader.readInt("Выбор: ")) {
                case 1: showStudents();      break;
                case 2: createStudent();     break;
                case 3: deleteStudent();     break;
                case 4: updateTaskStatus();  break;
                case 0: back = true;         break;
                default: System.out.println("Неверный выбор.");
            }
        }
    }

    private void showStudents() {
        showAllGroups();
        int groupId = reader.readInt("ID группы: ");
        Optional<Group> group = groupService.getGroupById(groupId);
        if (!group.isPresent()) { System.out.println("Группа не найдена."); return; }
        List<Student> students = studentService.getStudentsByGroup(groupId);
        if (students.isEmpty()) { System.out.println("В группе нет студентов."); return; }
        System.out.println("\nСтуденты группы " + group.get().getName() + ":");
        printStudents(students);
    }

    private void createStudent() {
        showAllGroups();
        int groupId = reader.readInt("ID группы: ");
        if (!groupService.getGroupById(groupId).isPresent()) {
            System.out.println("Группа не найдена.");
            return;
        }
        String name = reader.readLine("Имя студента: ");
        if (name.isEmpty()) { System.out.println("Имя не может быть пустым."); return; }
        Student student = studentService.createStudent(name, groupId);
        System.out.println("Студент добавлен: [" + student.getId() + "] " + student.getName());
    }

    private void deleteStudent() {
        showAllGroups();
        int groupId = reader.readInt("ID группы: ");
        if (!groupService.getGroupById(groupId).isPresent()) {
            System.out.println("Группа не найдена.");
            return;
        }
        printStudents(studentService.getStudentsByGroup(groupId));
        int id = reader.readInt("ID студента для удаления: ");
        if (!studentService.getStudentById(id).isPresent()) {
            System.out.println("Студент не найден.");
            return;
        }
        studentService.deleteStudent(id);
        System.out.println("Студент удалён.");
    }

    private void updateTaskStatus() {
        showAllGroups();
        int groupId = reader.readInt("ID группы: ");
        if (!groupService.getGroupById(groupId).isPresent()) {
            System.out.println("Группа не найдена.");
            return;
        }
        printStudents(studentService.getStudentsByGroup(groupId));
        int studentId = reader.readInt("ID студента: ");
        if (!studentService.getStudentById(studentId).isPresent()) {
            System.out.println("Студент не найден.");
            return;
        }
        int taskNum = reader.readInt("Номер задачи (1-3): ");
        if (taskNum < 1 || taskNum > 3) { System.out.println("Номер задачи должен быть от 1 до 3."); return; }
        boolean status = reader.readBoolean("Сдал задачу?");
        studentService.updateTaskStatus(studentId, taskNum - 1, status);
        System.out.println("Статус задачи обновлён.");
    }

    // ── Отчёт ────────────────────────────────────────────────────────────────

    private void handleReport() {
        showAllGroups();
        int groupId = reader.readInt("ID группы: ");
        Optional<Group> group = groupService.getGroupById(groupId);
        if (!group.isPresent()) { System.out.println("Группа не найдена."); return; }

        List<Student> students = studentService.getStudentsByGroup(groupId);
        if (students.isEmpty()) { System.out.println("В группе нет студентов."); return; }

        System.out.println("\nОтчёт по группе: " + group.get().getName());

        System.out.println("\nСдали все задачи:");
        boolean anyDone = false;
        for (Student s : students) {
            if (s.getTaskStatus(0) && s.getTaskStatus(1) && s.getTaskStatus(2)) {
                System.out.println("  + " + s.getName());
                anyDone = true;
            }
        }
        if (!anyDone) System.out.println("  (никто)");

        System.out.println("\nЕсть несданные задачи:");
        boolean anyPending = false;
        for (Student s : students) {
            if (!s.getTaskStatus(0) || !s.getTaskStatus(1) || !s.getTaskStatus(2)) {
                StringBuilder missing = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                    if (!s.getTaskStatus(i)) {
                        if (missing.length() > 0) missing.append(", ");
                        missing.append("Задача ").append(i + 1);
                    }
                }
                System.out.println("  - " + s.getName() + " (не сдал: " + missing + ")");
                anyPending = true;
            }
        }
        if (!anyPending) System.out.println("  (нет)");

        int done = 0;
        for (Student s : students) {
            if (s.getTaskStatus(0) && s.getTaskStatus(1) && s.getTaskStatus(2)) done++;
        }
        System.out.printf("%nИтого: %d из %d студентов сдали все задачи%n", done, students.size());
    }

    // ── Вспомогательные ──────────────────────────────────────────────────────

    private void printStudents(List<Student> students) {
        if (students.isEmpty()) { System.out.println("  Студентов нет."); return; }
        System.out.printf("  %-5s %-25s %-10s %-10s %-10s%n", "ID", "Имя", "Задача 1", "Задача 2", "Задача 3");
        System.out.println("  " + "-".repeat(62));
        for (Student s : students) {
            System.out.printf("  %-5d %-25s %-10s %-10s %-10s%n",
                    s.getId(), s.getName(),
                    s.getTaskStatus(0) ? "Сдал" : "Не сдал",
                    s.getTaskStatus(1) ? "Сдал" : "Не сдал",
                    s.getTaskStatus(2) ? "Сдал" : "Не сдал");
        }
    }
}
