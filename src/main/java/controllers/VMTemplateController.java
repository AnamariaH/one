package controllers;

import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.template.Template;
import service.onedb.GroupService;
import service.onedb.UsersOneService;
import service.onedb.VMTemplateService;

import java.io.IOException;

public class VMTemplateController {

    private GroupService groupService = new GroupService();
    private UsersOneService usersOneService = new UsersOneService();
    private VMTemplateService vmTemplateService = new VMTemplateService();

    public int createTemplate(String userName, String templateName, String imageName, int templateMemory, int cpu) throws ClientConfigurationException, IOException {
        Template newTemplate = vmTemplateService.createVMTemplate(templateName, imageName, templateMemory, cpu);
        int userId = usersOneService.getUserId(userName);
        newTemplate.chown(usersOneService.getUserId(userName));
        newTemplate.chgrp(groupService.getUserGroup(userId));
        newTemplate.chmod(740);
        return newTemplate.id();
    }

    public void removeTemplate(String userName, int templateId) throws ClientConfigurationException, IOException {
        if (vmTemplateService.getTemplateById(templateId).uid() == usersOneService.getUserId(userName)) {
            vmTemplateService.deleteTemplate(templateId);
        }
    }

    public void changeTemplatePermissions(String userName, int templateId, int permissionCode) throws ClientConfigurationException {
        if (vmTemplateService.getTemplateById(templateId).uid() == usersOneService.getUserId(userName)) {
            vmTemplateService.changePermissions(templateId, permissionCode);
        }
    }

}

