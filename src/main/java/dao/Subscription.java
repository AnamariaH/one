package dao;

public class Subscription {

    private int id;
    private User user;
    private Course course;

    public Subscription() {
    }

    public Subscription(User user_id, Course course_id) {
        this.user = user_id;
        this.course = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
