package controllers;

import dao.Student;
import dao.Teacher;
import dto.EnrollToCourseDTO;
import org.opennebula.client.ClientConfigurationException;
import service.db.DbUserService;
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

    public void createStudents(String filename) throws IOException, JAXBException {
        List<Student> studentsFromFile = FileUtils.getStudentsFromFile(filename);
        for (Student student : studentsFromFile) {
            usersOneService.createStudent(student.getName());
//            student.setOneId(usersOneService.createStudent(student.getName()));
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

    public void enrollUsersToCourse(String filename) throws ClientConfigurationException, JAXBException, IOException {
        List<EnrollToCourseDTO> enrollToCourses = FileUtils.enrollUsersToCourseFromFile(filename);
        for (EnrollToCourseDTO enroll : enrollToCourses) {
            if (enroll.getUserRole().equals("student")) {
                usersOneService.enrollUserToCourse(enroll.getUserId(),
                        groupService.getGroupId(enroll.getCourseId()));
            } else {
                if (enroll.getUserRole().equals("teacher")) {
                    usersOneService.enrollUserToCourse(enroll.getUserId(), groupService.getGroupId(enroll.getCourseId()));
                }
            }
            //dbUserService.insertTeacher(teacher);
        }
    }

    public void enroll(String filenameS, String filenameT, String filenameE) throws IOException, JAXBException, ClientConfigurationException {
        createStudents(filenameS);
        createTeachers(filenameT);
        enrollUsersToCourse(filenameE);
    }

}
