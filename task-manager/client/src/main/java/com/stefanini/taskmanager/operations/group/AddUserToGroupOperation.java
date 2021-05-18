package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.GroupServiceImpl;

public class AddUserToGroupOperation implements Operation {

    Group group = null;
    User user = null;
    GroupService groupService = new GroupServiceImpl();

    public AddUserToGroupOperation(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    @Override
    public void execute() {
        groupService.addUserToGroup(group, user);
    }

}
