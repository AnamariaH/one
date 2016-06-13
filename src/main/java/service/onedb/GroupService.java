package service.onedb;

import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.group.Group;
import org.opennebula.client.group.GroupPool;
import org.opennebula.client.user.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import test.FileUtils;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;

public class GroupService {

    public FileUtils users = new FileUtils();
    public UsersOneService usersOneService = new UsersOneService();
    Client oneClient;

    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

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

    public int createCourse(String courseName) {
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

        rc = Group.allocate(oneClient, courseName);

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

    public void setGroupQuota(int groupId, int cpu, int memory, int vmNumber, int volatileSize) throws ClientConfigurationException {
        OneResponse rc = getGroupById(groupId).setQuota("VM=[\n" +
                "\"  CPU= " + cpu + ",\n" +
                "\"  MEMORY= " + memory + ",\n" +
                "\"  VMS=" + vmNumber + ",\n" +
                "\"  VOLATILE_SIZE=\"" + volatileSize + "\"]");
        System.out.println(rc.getMessage());
    }

    public int getUserGroup(int userId) {
        int groupId = 0;
        User user = null;
        try {
            user = usersOneService.getUserById(userId);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = loadXMLFromString(user.info().getMessage());
            NodeList nodeList = doc.getElementsByTagName("GROUPS");
            Element e = (Element) nodeList.item(0);
            NodeList groupIdNodes = e.getElementsByTagName("ID");
            groupId = Integer.parseInt(groupIdNodes.item(0).getTextContent());
            System.out.println("----------------------------");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return groupId;
    }

}
