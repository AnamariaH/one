package service.onedb;

import dao.Student;
import dao.Teacher;
import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.user.User;
import org.opennebula.client.user.UserPool;
import service.db.DbUserService;
import test.FileUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersOneService {

    public FileUtils users = new FileUtils();
    Client oneClient;

    public List<User> createStudents(String filename) throws IOException, JAXBException {

        try {
            oneClient = new Client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        User newStudent;
        UserPool userpool = new UserPool(oneClient);
        OneResponse rc = userpool.info();
        List<User> users = new ArrayList<User>();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }
        DbUserService dbUserService = new DbUserService();
        List<Student> studentsFromFile = FileUtils.getStudentsFromFile(filename);
        for (Student student : studentsFromFile) {
            rc = User.allocate(oneClient, student.getName(), "pass1234");

            if (rc.isError()) {
                System.out.println(rc.getErrorMessage());
            }
            int userID = Integer.parseInt(rc.getMessage());
            student.setOneId(userID);
            dbUserService.insertStudent(student);

            System.out.println("The allocation request returned this ID: " + userID);

            newStudent = new User(userID, oneClient);
            users.add(newStudent);

            rc = newStudent.info();
            if (rc.isError()) {
                System.out.println(rc.getErrorMessage());
            }
            System.out.println(newStudent.getId() + "\t\t" + newStudent.getName());
        }
        System.out.println(rc.getMessage());
        userpool.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }
        return users;
    }

    public int createUser(String userName) {
        try {
            oneClient = new Client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        User newUser;
        UserPool userpool = new UserPool(oneClient);
        OneResponse rc = userpool.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        rc = User.allocate(oneClient, userName, "pass1234");

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        int userId = Integer.parseInt(rc.getMessage());
        System.out.println("The allocation request returned this ID: " + userId);

        newUser = new User(userId, oneClient);

        rc = newUser.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        userpool.info();
        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }
        return userId;
    }

    public List<User> createTeachers(String filename) throws IOException, JAXBException {
        Client oneClient = null;
        try {
            oneClient = new Client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        User newTeacher;
        UserPool userpool = new UserPool(oneClient);
        OneResponse rc = userpool.info();
        List<User> users = new ArrayList<User>();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }
        DbUserService dbUserService = new DbUserService();
        List<Teacher> teachersFromFile = FileUtils.getTeachersFromFile(filename);
        for (Teacher teacher : teachersFromFile) {
            rc = User.allocate(oneClient, teacher.getName(), "passw");

            if (rc.isError()) {
                System.out.println(rc.getErrorMessage());
            }
            int userID = Integer.parseInt(rc.getMessage());
            teacher.setOneId(userID);
            dbUserService.insertTeacher(teacher);

            System.out.println("The allocation request returned this ID: " + userID);

            newTeacher = new User(userID, oneClient);
            users.add(newTeacher);

            rc = newTeacher.info();
            if (rc.isError()) {
                System.out.println(rc.getErrorMessage());
            }
            System.out.println(newTeacher.getId() + "\t\t" + newTeacher.getName());
        }
        System.out.println(rc.getMessage());
        userpool.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }
        return users;
    }

    public int createTeacher(String teacherName) {
        try {
            oneClient = new Client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        User newTeacher;
        UserPool userpool = new UserPool(oneClient);
        OneResponse rc = userpool.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        rc = User.allocate(oneClient, teacherName, "passw");

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        int userId = Integer.parseInt(rc.getMessage());
        System.out.println("The allocation request returned this ID: " + userId);

        newTeacher = new User(userId, oneClient);

        rc = newTeacher.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        userpool.info();
        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }
        return userId;
    }

    public User getUserById(int id) throws ClientConfigurationException {
        oneClient = new Client();
        UserPool userPool = new UserPool(oneClient);
        userPool.info();
        System.out.print(userPool.info());
        return userPool.getById(id);
    }

    public void changeUserGroup(int userId, int groupId) throws ClientConfigurationException {
        OneResponse rc = getUserById(userId).chgrp(groupId);
    }

    public void deleteUser(int userId) throws ClientConfigurationException {
        getUserById(userId).delete();
    }

    public void changePassword(int userId, String newPassword) throws ClientConfigurationException {
        getUserById(userId).passwd(newPassword);
    }

    public void setUserQuota(int userId, int cpu, int memory, int vmNumber, int volatileSize) throws ClientConfigurationException {
        OneResponse rc = getUserById(userId).setQuota("VM=[\n" +
                "\"  CPU= " + cpu + ",\n" +
                "\"  MEMORY= " + memory + ",\n" +
                "\"  VMS=" + vmNumber + ",\n" +
                "\"  VOLATILE_SIZE=\"" + volatileSize + "\"]");
        System.out.println(rc.getMessage());
    }

    public int getUserId(String name) throws ClientConfigurationException {
        oneClient = new Client();
        UserPool userPool = new UserPool(oneClient);
        userPool.info();
        for (User oneUser : userPool) {
            if (name.equals(oneUser.getName())) {
                return Integer.parseInt(oneUser.getId());
            }
        }
        throw new RuntimeException("User with name " + name + " not found");
    }

    public void enrollUserToCourse(String user, int groupId) throws IOException, JAXBException, ClientConfigurationException {
        getUserById(getUserId(user)).chgrp(groupId);
    }

}
