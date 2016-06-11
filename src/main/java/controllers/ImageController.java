package controllers;

import org.opennebula.client.ClientConfigurationException;
import service.onedb.GroupService;
import service.onedb.ImageService;
import service.onedb.UsersOneService;

import java.io.IOException;

public class ImageController {

    private UsersOneService usersOneService = new UsersOneService();
    private GroupService groupService = new GroupService();
    private ImageService imageService = new ImageService();

    public void createImage(int courseId, String userName, String imageURL) throws ClientConfigurationException, IOException {
        if (groupService.getGroupById(courseId).contains(usersOneService.getUserId(userName))) {
            imageService.uploadImage(imageURL);
        }
    }
}
