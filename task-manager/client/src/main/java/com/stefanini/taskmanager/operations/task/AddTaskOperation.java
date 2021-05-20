package com.stefanini.taskmanager.operations.task;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.factory.ServiceType;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProduction;

public class AddTaskOperation implements Operation {
  private final Task task;
  private final User user;
  private final ServiceFactory serviceFactory =
          ServiceFactoryProduction.createServiceFactory(ServiceType.SERVICE_TYPE.value);
  private final TaskService taskService;

  {
    assert serviceFactory != null;
    taskService = serviceFactory.getTaskService();
  }

  public AddTaskOperation(Task task, User user) {
    this.task = task;
    this.user = user;
  }

  @Override
  public void execute() {
    taskService.addTask(task, user);
  }
}
