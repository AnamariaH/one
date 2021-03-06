package service.onedb;

import org.apache.commons.io.FileUtils;
import org.apache.xmlrpc.webserver.ServletWebServer;
import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.image.Image;
import org.opennebula.client.image.ImagePool;
import org.opennebula.client.template.Template;
import org.opennebula.client.user.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

public class ImageService {

    public static DatastoreService ds = new DatastoreService();
    public VMTemplateService vmTemplateService=new VMTemplateService();
    static Client oneClient;

    public String getImageFromURL(String imageURL) throws IOException {
        URL url = new URL(imageURL);
        File file = new File("/home/anamaria/IdeaProjects/cloud/src/main/resources/image.pdf");
        FileUtils.copyURLToFile(url, file);
        System.out.println("test "+file.getPath());
        return file.getName();
    }

    public void uploadImage(String imageURL) throws ClientConfigurationException, IOException {
        oneClient = new Client();

        String description =
                "NAME = \"" + getImageFromURL(imageURL) +"\"\n" +
                        "PATH = /home/anamaria/School/Licenta/src/main/resources/\n" +
                        "TYPE = \"OS\"\n" +
                        "DESCRIPTION = \"VAL2\"";

//        String description =
//                "NAME = \"ttylinux.img\"\n" +
//                        "PATH = /home/anamaria/School/Licenta/src/main/resources/\n" +
//                        "TYPE = \"OS\"\n" +
//                        "DESCRIPTION = \"VAL2\"";
        OneResponse rc = Image.allocate(oneClient, description, 107);
        if (rc.isError()) {
            System.out.println("failed!");
            System.out.println(rc.getErrorMessage());
            throw new ServletWebServer.Exception(1, rc.getErrorMessage(), "ll");
        }
        // The response message is the new VM's ID
        int newImgID = Integer.parseInt(rc.getMessage());
        System.out.println("ok, ID " + newImgID);

        Image img = new Image(newImgID, oneClient);

        rc = img.info();

        if (rc.isError())
            System.out.println("failed!");

        System.out.println(rc.getMessage() + "\n");

        System.out.println("The new Image " + img.getName());
    }

    public void createImage() {
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

            Image img = new Image(newImgID, oneClient);

            rc = img.info();

            if (rc.isError())
                System.out.println("failed!");
            //throw new Exception( rc.getErrorMessage() );

            System.out.println();
            System.out.println(
                    "This is the information OpenNebula stores for the new Image:");
            System.out.println(rc.getMessage() + "\n");

            System.out.println("The new Image " +
                    img.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Image getImageByID(int imageID) throws ClientConfigurationException {
        oneClient = new Client();
        ImagePool imagePool = new ImagePool(oneClient);
        imagePool.infoAll();
        System.out.print(imagePool.infoAll());
        return imagePool.getById(imageID);
    }

    public int getImageId(String imageName) throws ClientConfigurationException {
        oneClient = new Client();
        ImagePool imagePool = new ImagePool(oneClient);
        imagePool.info();
        for (Image image : imagePool) {
            if (imageName.equals(image.getName())) {
                return Integer.parseInt(image.getId());
            }
        }
        throw new RuntimeException("Group with name " + imageName + " not found");
    }

    public void deleteImage(int imageID) throws ClientConfigurationException {
        getImageByID(imageID).delete();
    }

    public void changeImageGroup(int imageID, int groupID) throws ClientConfigurationException {
        OneResponse rc = getImageByID(imageID).chgrp(groupID);
        System.out.println(rc.getMessage());
    }

    public void changeImageOwner(int imageID, int userID) throws ClientConfigurationException {
        OneResponse rc = getImageByID(imageID).chown(userID);
        System.out.println(rc.getMessage());
    }

    public void changeStateToEnabled(int imageId) throws ClientConfigurationException {
        getImageByID(imageId).enable();
    }

    public void changeImagePermissions(int imageID, int permissionCode) throws ClientConfigurationException {
        OneResponse rc = getImageByID(imageID).chmod(permissionCode);
        System.out.println(rc.getMessage());
    }

    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public String getTemplateImage(int templateId) {
        String imageName = null;
        Template template = null;
        try {
            template = vmTemplateService.getTemplateById(templateId);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = loadXMLFromString(template.info().getMessage());
            NodeList nodeList = doc.getElementsByTagName("DISK");
            Element e = (Element) nodeList.item(0);
            NodeList imageId = e.getElementsByTagName("IMAGE");
            imageName = imageId.item(0).getTextContent();
            System.out.println("----------------------------");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return imageName;
    }

}
