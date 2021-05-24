package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;
import com.stefanini.taskmanager.service.factory.ServiceType;
import com.stefanini.taskmanager.util.ApplicationProperties;

/**
 * Implements {@link Operation}. Encapsulates {@link Group} and {@link Task} fields. The execution
 * consists of sending the encapsulated fields to the {@link GroupService#addTaskToGroup(Group,
 * Task)} method as parameters.
 */
public class AddTaskToGroupOperation implements Operation {
  private final Group group;
  private final Task task;
  private ServiceFactory serviceFactory = null;
  private final ServiceType serviceType = ApplicationProperties.getInstance().getServiceType();

  {
    try {
      serviceFactory = ServiceFactoryProvider.createServiceFactory(serviceType);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final GroupService groupService = serviceFactory.getGroupService();;

  public AddTaskToGroupOperation(Group group, Task task) {
    this.group = group;
    this.task = task;
  }

  @Override
  public void execute() {
    groupService.addTaskToGroup(group, task);
  }
}
