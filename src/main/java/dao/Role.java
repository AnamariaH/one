package dao;

public class Role {

    private int id;
    private String role_type;

    public Role() {
    }

    public Role(int id, String role_type) {
        this.id = id;
        this.role_type = role_type;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
