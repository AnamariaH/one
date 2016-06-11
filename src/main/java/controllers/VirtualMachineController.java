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


    public VirtualMachine createVirtualMachine(String userName, int templateId, String vmName) throws ClientConfigurationException {
        VirtualMachine vm = virtualMachineService.createVM(templateId, vmName);
        int userId = usersOneService.getUserId(userName);
        vm.chown(userId);
        vm.chgrp(groupService.getUserGroup(userId));
        vm.chmod(740);
        return vm;
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

    public void stopVM(String userName, int vmId) throws ClientConfigurationException {
        if (virtualMachineService.getVMById(vmId).uid() == usersOneService.getUserId(userName)) {
            virtualMachineService.stopVm(vmId);
        }
    }

    public void shutdownVm(String userName, int vmId) throws ClientConfigurationException {
        if (virtualMachineService.getVMById(vmId).uid() == usersOneService.getUserId(userName)) {
            virtualMachineService.shutdownVm(vmId);
        }
    }

    public void holdVm(String userName, int vmId) throws ClientConfigurationException {
        if (virtualMachineService.getVMById(vmId).uid() == usersOneService.getUserId(userName)) {
            virtualMachineService.changeStatusToHold(vmId);
        }
    }

    public void powerOffVm(String userName, int vmId) throws ClientConfigurationException {
        if (virtualMachineService.getVMById(vmId).uid() == usersOneService.getUserId(userName)) {
            virtualMachineService.changeStatusToPowerOff(vmId);
        }
    }

    public void suspendVm(String userName, int vmId) throws ClientConfigurationException {
        if (virtualMachineService.getVMById(vmId).uid() == usersOneService.getUserId(userName)) {
            virtualMachineService.suspendVm(vmId);
        }
    }

    public void resumeVm(String userName, int vmId) throws ClientConfigurationException {
        if (virtualMachineService.getVMById(vmId).uid() == usersOneService.getUserId(userName)) {
            virtualMachineService.resumeVm(vmId);
        }
    }

    public void deployVM(int vmId, int hostId) throws ClientConfigurationException {
        virtualMachineService.deployVM(vmId, hostId);
    }

}
