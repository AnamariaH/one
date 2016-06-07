package controllers;

import org.opennebula.client.ClientConfigurationException;
import service.onedb.GroupService;
import service.onedb.UsersOneService;
import service.onedb.VMTemplateService;
import service.onedb.VirtualMachineService;

public class VirtualMachineController {

    private GroupService groupService=new GroupService();
    private UsersOneService usersOneService = new UsersOneService();
    private VirtualMachineService virtualMachineService= new VirtualMachineService();


    public void createVirtualMachine(String courseName, String userName, int templateId) throws ClientConfigurationException {
        if (groupService.getGroupById(groupService.getGroupId(courseName))
                .contains(usersOneService.getUserId(userName))) {
            virtualMachineService.createVM(templateId).chown(usersOneService.getUserId(userName));
        }

    }

}
