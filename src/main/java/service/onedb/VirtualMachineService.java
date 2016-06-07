package service.onedb;


import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vm.VirtualMachinePool;

public class VirtualMachineService {

    Client oneClient;

    public VirtualMachine createVM(int templateId) throws ClientConfigurationException {
        oneClient = new Client();
        String vmTemplate =
                "NAME     = vm_test_3   CPU = 1    MEMORY = 128\n" +
                        "CONTEXT = [ NETWORK = \"YES\", TARGET = \"hdb\" ]\n" +
                        "DISK = [ DATASTORE = \"default\", DATASTORE_ID = \"1\", \n" +
                        "DEV_PREFIX = \"hd\", DISK_ID = \"0\", \n" +
                        "DISK_SNAPSHOT_TOTAL_SIZE = \"0\", LN_TARGET = \"NONE\", \n" +
                        "IMAGE = \"ttylinux - kvm_file0\", IMAGE_ID = \"5\", IMAGE_UNAME = \"oneadmin\", \n"+
                        "TARGET = \"hda\", TM_MAD = \"shared\"] \n" +
                        "TEMPLATE_ID     = " + templateId +
                        "\n" +
                        "GRAPHICS = [\n" +
                        "  TYPE    = \"VNC\",\n" +
                        " PORT = \"5921\", \n" +
                        "  LISTEN  = \"0.0.0.0\"]";

        System.out.print("Trying to allocate the virtual machine... ");
        OneResponse rc = VirtualMachine.allocate(oneClient, vmTemplate);

        if (rc.isError()) {
            System.out.println("failed!");
            System.out.println(rc.getErrorMessage());
        }

        int newVMID = Integer.parseInt(rc.getMessage());
        System.out.println("ok, ID " + newVMID + ".");

        VirtualMachine virtualMachine = new VirtualMachine(newVMID, oneClient);
        rc = virtualMachine.info();

        if (rc.isError())
            System.out.println("failed!");

        return virtualMachine;
    }

    public void deleteVM(int id) throws ClientConfigurationException {
        getVMById(id).delete();

    }

    public VirtualMachine getVMById(int id) throws ClientConfigurationException {
        oneClient = new Client();
        VirtualMachinePool vmpool = new VirtualMachinePool(oneClient);
        vmpool.infoAll();
        System.out.print(vmpool.infoAll());
        return vmpool.getById(id);
    }

    public void deployVM(int vmId, int hostId, boolean enforce, int dsId) throws ClientConfigurationException {
        //getVMById(vmID).deploy(hostId, enforce, dsId);
        OneResponse rc = getVMById(vmId).deploy(hostId, enforce, dsId);
        System.out.println(rc.getMessage());
    }

    public void changeVMGroup(int vmId, int groupId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).chgrp(groupId);
        System.out.println(rc.getMessage());
    }

    public void changeVMPermissions(int vmId, int permissionCode) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).chmod(permissionCode);
        System.out.println(rc.getMessage());
    }

    public void changeVMOwner(int vmId, int userId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).chown(userId);
        System.out.println(rc.getMessage());
    }

    public void changeStatusToPowerOff(int vmId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).poweroff();
        System.out.println(rc.getMessage());
    }

    public void changeStatusToHold(int vmId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).hold();
        System.out.println(rc.getMessage());
    }

    public void resumeVm(int vmId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).resume();
        System.out.println(rc.getMessage());
    }
}
