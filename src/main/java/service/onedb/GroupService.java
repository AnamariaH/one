package service.onedb;

import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.group.Group;
import org.opennebula.client.group.GroupPool;
import test.FileUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class GroupService {

    public FileUtils users = new FileUtils();
    Client oneClient;

    public Group createGroups(String filename) throws IOException, JAXBException {

        try {
            oneClient = new Client();
        } catch (ClientConfigurationException e) {
            e.printStackTrace();
        }
        Group newGroup = null;
        GroupPool groupPool = new GroupPool(oneClient);
        OneResponse rc = groupPool.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }
        for (int i = 0; i < users.getCoursesFromFile(filename).size(); i++) {
            rc = Group.allocate(oneClient, users.getCoursesFromFile(filename).get(i).getName());

            if (rc.isError()) {
                System.out.println(rc.getErrorMessage());
            }

            int groupId = Integer.parseInt(rc.getMessage());
            System.out.println("The allocation request returned this ID: " + groupId);

            newGroup = new Group(groupId, oneClient);

            rc = newGroup.info();

            if (rc.isError()) {
                System.out.println(rc.getErrorMessage());
            }
            System.out.println(newGroup.getId() + "\t\t" + newGroup.getName());

            System.out.println(rc.getMessage());
            groupPool.info();

            if (rc.isError()) {
                System.out.println(rc.getErrorMessage());
            }
        }
        System.out.println(rc.getMessage());
        return newGroup;
    }

    public int createCourse(String groupName) {
        try {
            oneClient = new Client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Group newCourse;
        GroupPool groupPool = new GroupPool(oneClient);
        OneResponse rc = groupPool.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        rc = Group.allocate(oneClient, groupName);

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        int groupId = Integer.parseInt(rc.getMessage());
        System.out.println("The allocation request returned this ID: " + groupId);

        newCourse = new Group(groupId, oneClient);

        rc = newCourse.info();

        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }

        groupPool.info();
        if (rc.isError()) {
            System.out.println(rc.getErrorMessage());
        }
        return groupId;
    }

    public Group getGroupById(int id) throws ClientConfigurationException {
        oneClient = new Client();
        GroupPool groupPool = new GroupPool(oneClient);
        groupPool.info();
        System.out.print(groupPool.info());
        return groupPool.getById(id);
    }

    public void deleteGroup(int id) throws ClientConfigurationException {
        getGroupById(id).delete();
    }

    public GroupPool getAllGroups() throws ClientConfigurationException {
        oneClient = new Client();
        GroupPool groupPool = new GroupPool(oneClient);
        groupPool.info();
        return groupPool;
    }

    public int getGroupId(String name) throws ClientConfigurationException {
        oneClient = new Client();
        GroupPool groupPool = new GroupPool(oneClient);
        groupPool.info();
        for (Group oneGroup : groupPool) {
            if (name.equals(oneGroup.getName())) {
                return Integer.parseInt(oneGroup.getId());
            }
        }
        throw new RuntimeException("Group with name " + name + " not found");
    }

}
