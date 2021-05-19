package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.UserService;

public interface ServiceFactory {

  UserService getUserService();

  TaskService getTaskService();

  GroupService getGroupService();
}
