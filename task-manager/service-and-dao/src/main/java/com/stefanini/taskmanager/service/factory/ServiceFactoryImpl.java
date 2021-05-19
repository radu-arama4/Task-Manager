package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.JdbcDaoFactory;
import com.stefanini.taskmanager.service.*;

public class ServiceFactoryImpl implements ServiceFactory {
  private final DaoFactory daoFactory = new JdbcDaoFactory();

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
