package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.service.ExtendedService;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

import java.util.List;

public class ExtendedServiceImpl implements ExtendedService {
    ServiceFactory serviceFactory = ServiceFactoryProvider.createServiceFactory();
    TaskService taskService = serviceFactory.getTaskService();
    UserService userService = serviceFactory.getUserService();

    @Override
    public void createUserWithTasks(UserTO user, List<TaskTO> tasks) {
        userService.createUser(user);
        taskService.addMultipleTasks(tasks, user);
    }
}
