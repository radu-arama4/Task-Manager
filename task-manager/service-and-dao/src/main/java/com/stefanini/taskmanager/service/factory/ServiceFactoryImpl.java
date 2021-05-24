package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactoryProvider;
import com.stefanini.taskmanager.persistence.dao.factory.DaoType;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.impl.GroupServiceImpl;
import com.stefanini.taskmanager.service.impl.TaskServiceImpl;
import com.stefanini.taskmanager.service.impl.UserServiceImpl;
import com.stefanini.taskmanager.util.ApplicationProperties;

public class ServiceFactoryImpl implements ServiceFactory {
  ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
  DaoType daoType = applicationProperties.getDaoType();
  private DaoFactory daoFactory = null;

  {
    try {
      daoFactory = DaoFactoryProvider.createDaoFactory(daoType);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public UserService getUserService() {
    return new UserServiceImpl(daoFactory);
  }

  @Override
  public TaskService getTaskService() {
    return new TaskServiceImpl(daoFactory);
  }

  @Override
  public GroupService getGroupService() {
    return new GroupServiceImpl(daoFactory);
  }
}
