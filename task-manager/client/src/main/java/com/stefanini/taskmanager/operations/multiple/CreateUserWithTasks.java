package com.stefanini.taskmanager.operations.multiple;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;

import java.util.List;

public class CreateUserWithTasks implements Operation{
    private final UserTO user;
    private final List<TaskTO> tasks;

    public CreateUserWithTasks(UserTO user, List<TaskTO> tasks) {
        this.user = user;
        this.tasks = tasks;
    }

    @Override
    public void execute() {

    }
}
