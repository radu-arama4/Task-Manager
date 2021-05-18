package com.stefanini.taskmanager.operations.task;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;

public class ShowTasksOperation implements Operation {

    private User user;
    private TaskService taskService = new TaskServiceImpl();

    public ShowTasksOperation(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        taskService.showTasks(user);
    }
}
