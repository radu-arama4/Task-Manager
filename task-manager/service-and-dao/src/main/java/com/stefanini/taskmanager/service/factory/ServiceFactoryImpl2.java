package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactoryProvider;
import com.stefanini.taskmanager.persistence.dao.factory.DaoType;
import com.stefanini.taskmanager.service.ExtendedService;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.impl.ExtendedServiceImpl;
import com.stefanini.taskmanager.service.impl.GroupServiceImpl;
import com.stefanini.taskmanager.service.impl.TaskServiceImpl;
import com.stefanini.taskmanager.service.impl.UserServiceImpl;
import com.stefanini.taskmanager.util.ApplicationProperties;
import com.stefanini.taskmanager.util.email.EmailProxy;
import com.stefanini.taskmanager.util.transaction.TransactionProxy;

// TODO rename
// TODO read decorator design pattern
public class ServiceFactoryImpl2 implements ServiceFactory {
  private final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
  private final DaoType daoType = applicationProperties.getDaoType();
  private final DaoFactory daoFactory = DaoFactoryProvider.createDaoFactory(daoType);

  @Override
  public UserService getUserService() {
    return (UserService)
        TransactionProxy.newInstance(EmailProxy.newInstance(new UserServiceImpl(daoFactory)));
  }

  @Override
  public TaskService getTaskService() {
    return (TaskService) EmailProxy.newInstance(new TaskServiceImpl(daoFactory));
  }

  @Override
  public GroupService getGroupService() {
    return (GroupService) EmailProxy.newInstance(new GroupServiceImpl(daoFactory));
  }

  @Override
  public ExtendedService getExtendedService() {
    return (ExtendedService) TransactionProxy.newInstance(new ExtendedServiceImpl(daoFactory));
  }
}
