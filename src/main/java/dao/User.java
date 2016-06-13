package dao;

public class User {

    private int id;
    private String name;
    private String email;
    private int id_moodle;

    public User() {

    }

    public User(String name, String email, int id_moodle) {
        this.name = name;
        this.email = email;
        this.id_moodle = id_moodle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_moodle() {
        return id_moodle;
    }

    public void setId_moodle(int id_moodle) {
        this.id_moodle = id_moodle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
