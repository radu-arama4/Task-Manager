package com.stefanini.taskmanager.operations.task;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;

public class AddTaskOperation implements Operation {

    private Task task;
    private User user;
    private TaskService taskService = new TaskServiceImpl();

    public AddTaskOperation(Task task, User user) {
        this.task = task;
        this.user = user;
    }

    @Override
    public void execute() {
        taskService.addTask(task, user);
    }
}
