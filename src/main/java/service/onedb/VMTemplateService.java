package service.onedb;

import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.template.Template;
import org.opennebula.client.template.TemplatePool;

public class VMTemplateService {

    Client oneClient;

    public Template createVMTemplate(String imageName) throws ClientConfigurationException {
        oneClient=new Client();
        String vmTemplate =
                "NAME   = test-temp1\n" +
                        "MEMORY = 128\n" +
                        "CONTEXT = [ NETWORK = \"YES\", SSH_PUBLIC_KEY = \"$USER[SSH_PUBLIC_KEY]\" ]\n" +
                        "CPU    = 1\n" +
                        "\n" +
                        "DISK = [ IMAGE  = \"ttylinux - kvm_file0\", IMAGE_UNAME = oneadmin ]\n" +
                        "\n" +
                        "GRAPHICS = [\n" +
                        "  TYPE    = \"vnc\",\n" +
                        "  LISTEN  = \"0.0.0.0\"]";

        System.out.print("Trying to allocate the virtual machine... ");
        OneResponse rc = Template.allocate(oneClient, vmTemplate);

        if (rc.isError()) {
            System.out.println("failed!");
            System.out.println(rc.getErrorMessage());
            //throw new Exception( rc.getErrorMessage() );
        }

        // The response message is the new VM's ID
        int newtempId = Integer.parseInt(rc.getMessage());
        System.out.println("ok, ID " + newtempId + ".");

        // We can create a representation for the new VM, using the returned
        // VM-ID
        Template temp = new Template(newtempId, oneClient);
        // And now we can request its information.
        rc = temp.info();

        if (rc.isError())
            System.out.println("failed!");
        //throw new Exception( rc.getErrorMessage() );

        System.out.println();
        System.out.println("This is the information OpenNebula stores for the new template:");
        System.out.println(rc.getMessage() + "\n");
        return temp;
    }

    public int getTemplateId(String templateName) throws ClientConfigurationException {
        oneClient = new Client();
        TemplatePool templatePool = new TemplatePool(oneClient);
        templatePool.info();
        for (Template oneTemplate : templatePool) {
            if (templateName.equals(oneTemplate.getName())) {
                return Integer.parseInt(oneTemplate.getId());
            }
        }
        throw new RuntimeException("Group with name " + templateName + " not found");
    }

    public Template getTemplateById(int id) throws ClientConfigurationException {
        oneClient = new Client();
        TemplatePool templatePool= new TemplatePool(oneClient);
        templatePool.infoAll();
        System.out.print(templatePool.infoAll());
        return templatePool.getById(id);
    }

    public void deleteTemplate(int templateId) throws ClientConfigurationException {
        getTemplateById(templateId).delete();

    }
}