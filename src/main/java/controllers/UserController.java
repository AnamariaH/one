package controllers;

import dao.*;
import dto.EnrollToCourseDTO;
import org.opennebula.client.ClientConfigurationException;
import service.db.*;
import service.onedb.GroupService;
import service.onedb.UsersOneService;
import test.FileUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public class UserController {

    private UsersOneService usersOneService = new UsersOneService();
    private GroupService groupService = new GroupService();
    private DbUserService dbUserService = new DbUserService();
    private DbGroupService dbGroupService = new DbGroupService();
    private DbSubscriptionService dbSubscriptionService = new DbSubscriptionService();
    private DbRoleService dbRoleService = new DbRoleService();
    private DbSubscriptionRoleService dbSubscriptionRoleService=new DbSubscriptionRoleService();

    public void createStudents(String filename) throws IOException, JAXBException {
        List<Student> studentsFromFile = FileUtils.getStudentsFromFile(filename);
        for (Student student : studentsFromFile) {
            usersOneService.createUser(student.getName());
//            student.setOneId(usersOneService.createUser(student.getName()));
//            dbUserService.insertStudent(student);
        }
    }

    public void createTeachers(String filename) throws IOException, JAXBException {
        List<Teacher> teachersFromFile = FileUtils.getTeachersFromFile(filename);
        for (Teacher teacher : teachersFromFile) {
            usersOneService.createTeacher(teacher.getName());
//            teacher.setOneId(usersOneService.createTeacher(teacher.getName()));
//            dbUserService.insertTeacher(teacher);
        }
    }

    public void createUsers(String filename) throws IOException, JAXBException {
        List<User> usersFromFile = FileUtils.getUsersFromFile(filename);
        for (User user : usersFromFile) {
            int idUserOne = usersOneService.createUser(user.getName());
            user.setId(idUserOne);
            dbUserService.insertUser(user);
        }
    }


    public void enrollUsersToCourse(String filename) throws ClientConfigurationException, JAXBException, IOException {
        List<EnrollToCourseDTO> enrollToCourses = FileUtils.enrollUsersToCourseFromFile(filename);
        for (EnrollToCourseDTO enroll : enrollToCourses) {
            if(groupService.getUserGroup(usersOneService.getUserId(enroll.getUserId())) != 1){
                usersOneService.enrollUserToSecondaryCourse(enroll.getUserId(), groupService.getGroupId(enroll.getCourseId() + "_" + enroll.getUserRole() + "s"));
            }
            else {
                usersOneService.enrollUserToCourse(enroll.getUserId(), groupService.getGroupId(enroll.getCourseId() + "_" + enroll.getUserRole() + "s"));
            }
            Subscription subscription = new Subscription(new User(usersOneService.getUserId(enroll.getUserId())), dbGroupService.getCourse(enroll.getCourseId()));
            dbSubscriptionService.insertSubscription(subscription);
            Role role=dbRoleService.getRole(enroll.getUserRole());
            dbSubscriptionRoleService.insertSubscriptionRole(new SubscriptionRole(subscription,role));
        }
    }

    public void enroll(String filenameUsers, String filenameEnrollemnt) throws IOException, JAXBException, ClientConfigurationException {
        createUsers(filenameUsers);
        enrollUsersToCourse(filenameEnrollemnt);
    }

    public void changeUserPassword(String userName, String newPassword) throws ClientConfigurationException {
        usersOneService.changePassword(usersOneService.getUserId(userName), newPassword);
    }

}
