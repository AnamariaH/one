package controllers;

import org.opennebula.client.ClientConfigurationException;
import service.onedb.GroupService;
import service.onedb.UsersOneService;
import service.onedb.VMTemplateService;

import java.io.IOException;

public class VMTemplateController {

    private GroupService groupService = new GroupService();
    private UsersOneService usersOneService = new UsersOneService();
    private VMTemplateService vmTemplateService = new VMTemplateService();

    public void createVMTemplate(int courseId, int userId, String imageName) throws ClientConfigurationException, IOException {
        if (groupService.getGroupById(courseId).contains(userId)) {
            vmTemplateService.createVMTemplate(imageName).chown(userId);
        }
    }

    public void createTemplate(String courseName, String userName, String imageName) throws ClientConfigurationException, IOException {
        if (groupService.getGroupById(groupService.getGroupId(courseName))
                .contains(usersOneService.getUserId(userName))) {
            vmTemplateService.createVMTemplate(imageName).chown(usersOneService.getUserId(userName));
        }
    }

    public void removeTemplate(String userName, int templateId) throws ClientConfigurationException, IOException {
        vmTemplateService.deleteTemplate(templateId);
    }
}

