package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactoryProduction;
import com.stefanini.taskmanager.service.*;
import com.stefanini.taskmanager.service.standard.GroupServiceImpl;
import com.stefanini.taskmanager.service.standard.TaskServiceImpl;
import com.stefanini.taskmanager.service.standard.UserServiceImpl;

public class ServiceFactoryImpl implements ServiceFactory {
  private final DaoFactory daoFactory = DaoFactoryProduction.createDaoFactory("jdbc");

  private boolean checkDao(){
    return daoFactory == null;
  }

  @Override
  public UserService getUserService() {
    if (checkDao()) return null;
    return new UserServiceImpl(daoFactory);
  }

  @Override
  public TaskService getTaskService() {
    if (checkDao()) return null;
    return new TaskServiceImpl(daoFactory);
  }

  @Override
  public GroupService getGroupService() {
    if (checkDao()) return null;
    return new GroupServiceImpl(daoFactory);
  }
}
