package test;

import controllers.GroupController;
import controllers.UserController;
import org.opennebula.client.ClientConfigurationException;
import service.db.DbUserService;
import service.onedb.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Main {

    private static Service service;
    private static VirtualMachineService vmService = new VirtualMachineService();
    private static DbUserService dbUserService;
    private static ImageService imageService=new ImageService();
    private static UsersOneService usersOneService = new UsersOneService();
    private static GroupService groupService = new GroupService();
    private static UserController userController = new UserController();
    private static GroupController groupController = new GroupController();
    private static VMTemplateService vmTemplateService = new VMTemplateService();

    public static void main(String[] args) throws ClientConfigurationException, IOException, JAXBException {
        //vmTemplateService.createVMTemplate("");
        vmService.createVM(vmTemplateService.getTemplateId("test-temp1"));
        //imageService.uploadImage();
//        userController.enroll("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/students.json",
//                "/home/anamaria/IdeaProjects/cloud/src/main/resources/json/teachers.json",
//                "/home/anamaria/IdeaProjects/cloud/src/main/resources/json/enrollUsersToCourse.json");
       // userController.createStudents("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/students.json");
        //userController.createTeachers("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/teachers.json");
      //  groupController.createGroups("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/courses.json");
// usersOneService=new UsersOneService();
//        usersOneService.setUserQuota(20, "VM=[\n" +
//                "  CPU=\"2\",\n" +
//                "  MEMORY=\"2048\",\n" +
//                "  VMS=\"1\",\n" +
//                "  VOLATILE_SIZE=\"-2\"\n" +
//                "]");
}
}