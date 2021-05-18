package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.GroupServiceImpl;

public class AddTaskToGroupOperation implements Operation {

    Group group = null;
    Task task = null;
    GroupService groupService = new GroupServiceImpl();

    public AddTaskToGroupOperation(Group group, Task task) {
        this.group = group;
        this.task = task;
    }

    @Override
    public void execute() {
        groupService.addTaskToGroup(group, task);
    }

}
