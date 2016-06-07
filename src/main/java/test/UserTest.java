package test;

import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.user.User;
import org.opennebula.client.user.UserPool;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class UserTest {

    private static FileUtils users=new FileUtils();

    public static void main(String[] args) throws IOException, JAXBException {
        Client oneClient;

        try {
            oneClient = new Client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        UserPool userpool = new UserPool(oneClient);
        OneResponse rc = userpool.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
            return;
        }

        System.out.println("Allocating new user (javaUser,javaPassword)...");
        rc = User.allocate(oneClient, users.getStudentsFromFile("").get(0).getName(), "javaPassword");

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
            return;
        }

        int userID = Integer.parseInt(rc.getMessage());
        System.out.println("The allocation request returned this ID: " + userID);

        User javaUser = new User(userID, oneClient);

        rc = javaUser.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
            return;
        }

        // This is how the info returned looks like...
        System.out.println("Info for " + javaUser.xpath("name") + "...");
        System.out.println(rc.getMessage());

        userpool.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
            return;
        }

        System.out.println(rc.getMessage());

    }

}
