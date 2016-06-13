package test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Course;
import dao.Student;
import dao.Teacher;
import dao.User;
import dto.EnrollToCourseDTO;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    public static List<Student> getStudentsFromFile(String fileName) throws IOException, JAXBException {
        ObjectMapper mapper = new ObjectMapper();

        List<Student> students = mapper.readValue(new File(fileName), new TypeReference<List<Student>>() {
        });
        return students;
    }

    public static List<Teacher> getTeachersFromFile(String fileName) throws IOException, JAXBException {
        ObjectMapper mapper = new ObjectMapper();

        List<Teacher> teachers = mapper.readValue(new File(fileName), new TypeReference<List<Teacher>>() {
        });
        return teachers;
    }

    public static List<User> getUsersFromFile(String fileName) throws IOException, JAXBException {
        ObjectMapper mapper = new ObjectMapper();

        List<User> users = mapper.readValue(new File(fileName), new TypeReference<List<User>>() {
        });
        return users;
    }

    public static List<Course> getCoursesFromFile(String fileName) throws IOException, JAXBException {
        ObjectMapper mapper = new ObjectMapper();

        List<Course> courses = mapper.readValue(new File(fileName), new TypeReference<List<Course>>() {
        });
        return courses;
    }

    public static List<EnrollToCourseDTO> enrollUsersToCourseFromFile(String fileName) throws IOException, JAXBException {
        ObjectMapper mapper = new ObjectMapper();

        List<EnrollToCourseDTO> courses = mapper.readValue(new File(fileName), new TypeReference<List<EnrollToCourseDTO>>() {
        });
        return courses;
    }

}
