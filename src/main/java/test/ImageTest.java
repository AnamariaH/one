package test;

import org.apache.xmlrpc.webserver.ServletWebServer;
import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.image.Image;
import service.onedb.DatastoreService;

public class ImageTest {

    static Client oneClient;
    public static DatastoreService ds = new DatastoreService();

    public static void main(String[] args) {
        try {
            oneClient = new Client();


            // This VM template is a valid test, but it will probably fail to run
            // if we try to deploy it; the path for the image is unlikely to
            // exist.
            String description =
                    "NAME = \"test_im_123123\"\n" +
                            "PATH = /home/anamaria/School/Licenta/src/main/resources/\n" +
                            "TYPE = \"OS\"\n" +
                            "DESCRIPTION = \"VAL2\"";

            System.out.print("Trying to allocate image... ");
            OneResponse rc = Image.allocate(oneClient, description, Integer.parseInt(ds.createDS().getId()));

            if (rc.isError()) {
                System.out.println("failed!");
                System.out.println(rc.getErrorMessage());
                throw new ServletWebServer.Exception(1, rc.getErrorMessage(), "ll");
            }

            // The response message is the new VM's ID
            int newImgID = Integer.parseInt(rc.getMessage());
            System.out.println("ok, ID " + newImgID + ".");

            // We can create a representation for the new VM, using the returned
            // VM-ID
            Image img = new Image(newImgID, oneClient);


            // And now we can request its information.
            rc = img.info();

            if (rc.isError())
                System.out.println("failed!");
            //throw new Exception( rc.getErrorMessage() );

            System.out.println();
            System.out.println(
                    "This is the information OpenNebula stores for the new Image:");
            System.out.println(rc.getMessage() + "\n");

            // This VirtualMachine object has some helpers, so we can access its
            // attributes easily (remember to load the data first using the info
            // method).
            System.out.println("The new Image " +
                    img.getName());

            // And we can also use xpath expressions
            System.out.println("The path of the disk is");
            System.out.println("\t" + img.xpath("template/disk/source"));

            // We have also some useful helpers for the actions you can perform
            // on a virtual machine, like cancel or finalize:

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
