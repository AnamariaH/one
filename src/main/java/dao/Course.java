package dao;

public class Course {

    private int id;
    private String name;
    private int students_group;
    private int teachers_group;

    public Course() {
    }

    public Course(String name, int students_group, int teachers_group) {
        this.name = name;
        this.students_group = students_group;
        this.teachers_group = teachers_group;
    }

    public int getStudents_group() {
        return students_group;
    }

    public void setStudents_group(int students_group) {
        this.students_group = students_group;
    }

    public int getTeachers_group() {
        return teachers_group;
    }

    public void setTeachers_group(int teachers_group) {
        this.teachers_group = teachers_group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
