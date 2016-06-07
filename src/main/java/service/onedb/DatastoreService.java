package service.onedb;

import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.datastore.Datastore;

public class DatastoreService {

    static Client oneClient;
    int n = 5;

    public Datastore createDS() {
        org.opennebula.client.datastore.Datastore ds = null;
        n++;
        try {
            oneClient = new Client();

            // This VM template is a valid test, but it will probably fail to run
            // if we try to deploy it; the path for the image is unlikely to
            // exist.
            String description =
                    "NAME = \"test_img " + n + "\"\n" +
                            "DISK_TYPE = FILE\n" +
                            "DS_MAD = fs\n" +
                            "TM_MAD = shared\n" +
                            "TYPE = \"IMAGE\"\n";

            System.out.print("Trying to allocate datastore... ");
            OneResponse rc = Datastore.allocate(oneClient, description);

            if (rc.isError()) {
                System.out.println("failed!");
                System.out.println(rc.getErrorMessage());
                //throw new Exception( rc.getErrorMessage() );
            }

            // The response message is the new VM's ID
            int newDatastoreID = Integer.parseInt(rc.getMessage());
            System.out.println("ok, ID " + newDatastoreID + ".");

            // We can create a representation for the new VM, using the returned
            // VM-ID
            ds = new Datastore(newDatastoreID, oneClient);


            // And now we can request its information.
            rc = ds.info();

            if (rc.isError())
                System.out.println("failed!");
            //throw new Exception( rc.getErrorMessage() );

            System.out.println();
            System.out.println(
                    "This is the information OpenNebula stores for the new datastore:");
            System.out.println(rc.getMessage() + "\n");

            // This VirtualMachine object has some helpers, so we can access its
            // attributes easily (remember to load the data first using the info
            // method).
            System.out.println("The new datastore " +
                    ds.getName());

            // We have also some useful helpers for the actions you can perform
            // on a virtual machine, like cancel or finalize:
            /// return ds;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

}
