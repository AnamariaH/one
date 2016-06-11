package service.onedb;


import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.template.Template;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vm.VirtualMachinePool;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class VirtualMachineService {

    public VMTemplateService vmTemplateService = new VMTemplateService();
    public ImageService imageService = new ImageService();
    Client oneClient;

    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public VirtualMachine createVM(int templateId, String vmName) throws ClientConfigurationException {
        oneClient = new Client();
        String vmTemplate =
                "NAME     = \"" + vmName + "\"" + "\n" +
                        "MEMORY = " + getTemplateMemory(templateId) + "\n" +
                        "CPU = " + getTemplateCPU(templateId) + "\n" +
                        "CONTEXT = [ NETWORK = \"YES\", TARGET = \"hdb\" ]\n" +
                        "DISK = [ DATASTORE = \"default\", DATASTORE_ID = \"1\", \n" +
                        "DEV_PREFIX = \"hd\", DISK_ID = \"0\", \n" +
                        "DISK_SNAPSHOT_TOTAL_SIZE = \"0\", LN_TARGET = \"NONE\", \n" +
                        "IMAGE = \"" + imageService.getTemplateImage(templateId) + "\", " +
                        "IMAGE_ID = " + imageService.getImageId(imageService.getTemplateImage(templateId)) + ", " +
                        "IMAGE_UNAME = \"oneadmin\", \n" +
                        "TARGET = \"hda\", TM_MAD = \"shared\"] \n" +
                        "TEMPLATE_ID     = " + templateId + "\n" +
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

    public void deployVM(int vmId, int hostId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).deploy(hostId);
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

    public void suspendVm(int vmId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).suspend();
        System.out.println(rc.getMessage());
    }

    public void stopVm(int vmId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).stop();
        System.out.println(rc.getMessage());
    }

    public void shutdownVm(int vmId) throws ClientConfigurationException {
        OneResponse rc = getVMById(vmId).shutdown();
        System.out.println(rc.getMessage());
    }

    public int getTemplateMemory(int templateId) {
        int memory = 0;
        Template template = null;
        try {
            template = vmTemplateService.getTemplateById(templateId);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = loadXMLFromString(template.info().getMessage());
            NodeList nodeList = doc.getElementsByTagName("MEMORY");
            memory = Integer.parseInt(nodeList.item(0).getTextContent());
            System.out.println("----------------------------");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return memory;
    }

    public int getTemplateCPU(int templateId) {
        int cpu = 0;
        Template template = null;
        try {
            template = vmTemplateService.getTemplateById(templateId);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = loadXMLFromString(template.info().getMessage());
            NodeList nodeList = doc.getElementsByTagName("CPU");
            cpu = Integer.parseInt(nodeList.item(0).getTextContent());
            System.out.println("----------------------------");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cpu;
    }

}
