package controllers;

import dao.Course;
import service.db.DbGroupService;
import service.onedb.GroupService;
import test.FileUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public class GroupController {

    private GroupService groupService=new GroupService();
    private DbGroupService dbGroupService =new DbGroupService();

    public void createGroups(String filename) throws IOException, JAXBException {
        List<Course> coursesFromFile = FileUtils.getCoursesFromFile(filename);
        for (Course course: coursesFromFile) {
            course.setOneId(groupService.createCourse(course.getName()+"_teachers"));
            groupService.createCourse(course.getName()+"_students");
          //  dbGroupService.insertCourse(course);
        }
    }
}
