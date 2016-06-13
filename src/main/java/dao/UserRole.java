package dao;

public class UserRole {

    private int id;
    private User user_id;
    private Role role_id;
    private Course course_id;

    public UserRole() {
    }

    public UserRole(User user_id, Role role_id, Course course_id) {
        this.user_id = user_id;
        this.role_id = role_id;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Role getRole_id() {
        return role_id;
    }

    public void setRole_id(Role role_id) {
        this.role_id = role_id;
    }

    public Course getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Course course_id) {
        this.course_id = course_id;
    }
}
