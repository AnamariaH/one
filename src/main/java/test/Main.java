package test;

import controllers.GroupController;
import controllers.UserController;
import controllers.VMTemplateController;
import controllers.VirtualMachineController;
import org.opennebula.client.ClientConfigurationException;
import service.db.DbUserService;
import service.onedb.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Main {

    private static Service service;
    private static VirtualMachineService vmService = new VirtualMachineService();
    private static DbUserService dbUserService;
    private static ImageService imageService = new ImageService();
    private static UsersOneService usersOneService = new UsersOneService();
    private static GroupService groupService = new GroupService();
    private static UserController userController = new UserController();
    private static GroupController groupController = new GroupController();
    private static VMTemplateService vmTemplateService = new VMTemplateService();
    private static VMTemplateController vmTemplateController = new VMTemplateController();
    private static VirtualMachineController virtualMachineController = new VirtualMachineController();

    public static void main(String[] args) throws Exception {
//        groupController.createGroups("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/courses.json");
//        userController.enroll("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/students.json",
//                "/home/anamaria/IdeaProjects/cloud/src/main/resources/json/teachers.json",
//                "/home/anamaria/IdeaProjects/cloud/src/main/resources/json/enrollUsersToCourse.json");
        int id=vmTemplateController.createTemplate("t7","test-temp3","ttylinux_kvm_file0",64,1);
        virtualMachineController.createVirtualMachine("s6",30,"vm-test4").deploy(2);
     //   virtualMachineController.deployVM(48,2);
        //vmTemplateService.createVMTemplate("");
        //vmService.createVM(vmTemplateService.getTemplateId("test-temp1"));
        //imageService.uploadImage();
//
        // userController.createStudents("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/students.json");
        //userController.createTeachers("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/teachers.json");
// usersOneService=new UsersOneService();
//        usersOneService.setUserQuota(20, "VM=[\n" +
//                "  CPU=\"2\",\n" +
//                "  MEMORY=\"2048\",\n" +
//                "  VMS=\"1\",\n" +
//                "  VOLATILE_SIZE=\"-2\"\n" +
//                "]");
//        groupService.getUserGroup(1);
    }
}
