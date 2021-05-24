package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.UserService;

/**
 * Interface which provides methods for creating instances of {@link UserService}, {@link
 * TaskService} and {@link GroupService}.
 */
public interface ServiceFactory {
  UserService getUserService();

  TaskService getTaskService();

  GroupService getGroupService();
}
