package dao;

public class Course {

    private int id;
    private String name;
    private int studentsGroup;
    private int teachersGroup;

    public Course(int id, String name, int studentsGroup, int teachersGroup) {
        this.id = id;
        this.name = name;
        this.studentsGroup = studentsGroup;
        this.teachersGroup = teachersGroup;
    }

    public Course() {
    }

    public int getStudentsGroup() {
        return studentsGroup;
    }

    public void setStudentsGroup(int studentsGroup) {
        this.studentsGroup = studentsGroup;
    }

    public int getTeachersGroup() {
        return teachersGroup;
    }

    public void setTeachersGroup(int teachersGroup) {
        this.teachersGroup = teachersGroup;
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
