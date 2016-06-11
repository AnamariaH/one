package test;

import org.opennebula.client.ClientConfigurationException;
import service.db.DbUserService;
import service.onedb.ImageService;
import service.onedb.UsersOneService;
import service.onedb.VirtualMachineService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Service {

    private DbUserService dbUserService = new DbUserService();
    private UsersOneService usersOneService = new UsersOneService();
    private VirtualMachineService vmService = new VirtualMachineService();
    private ImageService imageService;

//    public void createVMByTeacher(int teacherId) {
//        if (dbUserService.getRoleById(teacherId).equals("professor")) {
//            vmService.createVM();
//        } else {
//            System.out.println("User is not a teacher!!");
//        }
//    }

    public void deleteVMByTeacher(int teacherId, int vmId) throws ClientConfigurationException {
        if (dbUserService.getRoleById(teacherId).equals("professor")) {
            vmService.deleteVM(vmId);
        } else {
            System.out.println("User is not a teacher!!");
        }
    }

    public void changeVMGroupByTeacher(int teacherId, int vmId, int groupId) throws ClientConfigurationException {
        if (dbUserService.getRoleById(teacherId).equals("professor")) {
            vmService.changeVMGroup(vmId, groupId);
        } else {
            System.out.println("User is not a teacher!!");
        }
    }

    public void changeVMOwnerByTeacher(int teacherId, int vmId, int userId) throws ClientConfigurationException {
        if (dbUserService.getRoleById(teacherId).equals("professor")) {
            vmService.changeVMOwner(vmId, userId);
        } else {
            System.out.println("User is not a teacher!!");
        }
    }

    public void changeVMPermissionsByTeacher(int teacherId, int vmId, int permissionCode) throws ClientConfigurationException {
        if (dbUserService.getRoleById(teacherId).equals("professor")) {
            vmService.changeVMPermissions(vmId, permissionCode);
        } else {
            System.out.println("User is not a teacher!!");
        }
    }

    public void deployVMByTeacher(int teacherId, int vmId, int hostId, boolean enforce, int dsId) throws ClientConfigurationException {
        if (dbUserService.getRoleById(teacherId).equals("professor")) {
            vmService.deployVM(vmId, hostId);
        } else {
            System.out.println("User is not a teacher!!");
        }
    }

    public void createImageByProfessor(int professorID) {
        if (dbUserService.getRoleById(professorID) == "professor") {
            imageService.createImage();
        }
    }

    public void createStudentsByTeacher(String filename, int teacherId) throws IOException, JAXBException {
        if (dbUserService.getRoleById(teacherId).equals("professor")) {
            usersOneService.createStudents(filename);
        } else {
            System.out.println("User is not a teacher!!");
        }
    }

    public void createTeachersByTeacher(String filename, int teacherId) throws IOException, JAXBException {
        if (dbUserService.getRoleById(teacherId).equals("professor")) {
            usersOneService.createTeachers(filename);
        } else {
            System.out.println("User is not a teacher!!");
        }
    }
}
