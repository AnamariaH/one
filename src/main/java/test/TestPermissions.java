package test;

import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vm.VirtualMachinePool;

public class TestPermissions {


    public static void main(String[] args) throws InterruptedException {
        Client oneClient = null;
        try {
            oneClient = new Client();
        } catch (ClientConfigurationException e) {
            e.printStackTrace();
        }
        VirtualMachinePool vmpool = new VirtualMachinePool(oneClient);
        System.out.print(vmpool.infoAll());
        VirtualMachine vm = vmpool.getById(17);
        System.out.println(vm);

        //OneResponse rc=vm.info();
        //System.out.println(rc.getMessage() + "\n");
        //vm.chgrp(1);
        //vm.chmod(777);
        vm.deploy(2, false, 0);
        //vm.migrate(2);
        OneResponse rc = vm.info();
        //System.out.println("test status " + vm.status().toString());
        //rc = vm.info();
        System.out.println(rc.getMessage() + "\n");

    }
}
