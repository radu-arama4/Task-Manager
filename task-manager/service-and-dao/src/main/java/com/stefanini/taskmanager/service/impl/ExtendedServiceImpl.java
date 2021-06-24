package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.EntityFactory;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.ExtendedService;
import com.stefanini.taskmanager.util.OperationsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Component
@Scope("singleton")
public class ExtendedServiceImpl implements ExtendedService {
  @Autowired
  @Qualifier("hibernate")
  private UserDao userDao;

  @Autowired
  @Qualifier("hibernate")
  private TaskDao taskDao;

  private static final Logger logger = LogManager.getLogger(ExtendedServiceImpl.class);

  @Autowired
  public ExtendedServiceImpl() {}

  @Override
  public void createUserWithTasks(UserTO user, Stream<TaskTO> tasks) {
    User newUser = EntityFactory.createUser();
    List<Task> newTasks = new LinkedList<>();

    OperationsUtil.copyObjectProperties(newUser, user);

    tasks.forEach(
        taskTO -> {
          Task task = EntityFactory.createTask();
          OperationsUtil.copyObjectProperties(task, taskTO);
          newTasks.add(task);
        });

    User createdUser = userDao.createUser(newUser);
    taskDao.addMultipleTasks(newTasks.stream(), createdUser);
  }
}
