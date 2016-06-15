package test;

import controllers.GroupController;
import controllers.UserController;
import controllers.VMTemplateController;
import controllers.VirtualMachineController;
import service.db.DbUserService;
import service.onedb.*;

public class Main {

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
        //userController.enrollUsersToCourse("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/enrollUsersToCourse.json");
        //userController.createUsers("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/users.json");
//        groupController.createCourses("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/courses.json");
        userController.enroll("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/users.json",
                "/home/anamaria/IdeaProjects/cloud/src/main/resources/json/enrollUsersToCourse.json");
//        int id=vmTemplateController.createTemplate("t11","test-temp4","ttylinux_kvm_file0",64,1);
//        virtualMachineController.createVirtualMachine("s10",id,"vm-test5").deploy(2);
        //virtualMachineController.stopVM("s11",56);
     //   virtualMachineController.deployVM(48,2);
        //vmTemplateService.createVMTemplate("");
        //vmService.createVM(vmTemplateService.getTemplateId("test-temp1"));
        //imageService.uploadImage();
//
        // userController.createStudents("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/students.json");
        //userController.createTeachers("/home/anamaria/IdeaProjects/cloud/src/main/resources/json/teachers.json");
// usersOneService=new UsersOneService();
        //usersOneService.setUserQuota(65, 3,2048,4,-2);
//        groupService.getUserGroup(1);
    }
}
