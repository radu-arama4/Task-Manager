package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactoryProvider;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.standard.GroupServiceImpl;
import com.stefanini.taskmanager.service.standard.TaskServiceImpl;
import com.stefanini.taskmanager.service.standard.UserServiceImpl;

import static com.stefanini.taskmanager.persistence.dao.factory.DaoType.*;

public class ServiceFactoryImpl implements ServiceFactory {
  private final DaoFactory daoFactory = DaoFactoryProvider.createDaoFactory(JDBC);

  private boolean checkDaoFactory(){
    return daoFactory == null;
  }

  @Override
  public UserService getUserService() {
    if (checkDaoFactory()) return null;
    return new UserServiceImpl(daoFactory);
  }

  @Override
  public TaskService getTaskService() {
    if (checkDaoFactory()) return null;
    return new TaskServiceImpl(daoFactory);
  }

  @Override
  public GroupService getGroupService() {
    if (checkDaoFactory()) return null;
    return new GroupServiceImpl(daoFactory);
  }
}
