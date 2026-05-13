package model;

public class Student {
    private final int id;
    private String name;
    private final int groupId;
    private final boolean[] tasks = new boolean[3];

    public Student(int id, String name, int groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getGroupId() { return groupId; }

    public boolean getTaskStatus(int index) { return tasks[index]; }
    public void setTaskStatus(int index, boolean status) { tasks[index] = status; }
}
