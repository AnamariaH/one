package dao;

public class Student {

    private int id;
    private String name;
    private String email;
    private int registrationNumber;
    private String role;
    private int oneId;

    public Student(String name, String email, int registrationNumber, String role, int oneId) {
        this.name = name;
        this.email = email;
        this.registrationNumber = registrationNumber;
        this.role = role;
        this.oneId = oneId;
    }

    public Student() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getOneId() {
        return oneId;
    }

    public void setOneId(int oneId) {
        this.oneId = oneId;
    }
}
