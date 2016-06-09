package controllers;

import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.vm.VirtualMachine;
import service.onedb.GroupService;
import service.onedb.UsersOneService;
import service.onedb.VirtualMachineService;

import java.io.IOException;

public class VirtualMachineController {

    private GroupService groupService = new GroupService();
    private UsersOneService usersOneService = new UsersOneService();
    private VirtualMachineService virtualMachineService = new VirtualMachineService();


    public void createVirtualMachine(String courseName, String userName, int templateId) throws ClientConfigurationException {
//        if (groupService.getGroupById(groupService.getGroupId(courseName))
//                .contains(usersOneService.getUserId(userName))) {
        VirtualMachine vm = virtualMachineService.createVM(templateId);
        int userId = usersOneService.getUserId(userName);
        vm.chown(userId);
        vm.chgrp(groupService.getUserGroup(userId));
    }

    public void removeVirtualMachine(String userName, int vmId) throws ClientConfigurationException, IOException {
        if (virtualMachineService.getVMById(vmId).uid() == usersOneService.getUserId(userName)) {
            virtualMachineService.deleteVM(vmId);
        }
    }

    public void changeVMPermissions(String userName, int vmId, int permissionCode) throws ClientConfigurationException {
        if (virtualMachineService.getVMById(vmId).uid() == usersOneService.getUserId(userName)) {
            virtualMachineService.changeVMPermissions(vmId, permissionCode);
        }
    }

}
