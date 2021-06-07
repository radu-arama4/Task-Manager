package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.entity.EntityFactory;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.ExtendedService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class ExtendedServiceImpl implements ExtendedService {
  UserDao userDao;
  TaskDao taskDao;

  public ExtendedServiceImpl(DaoFactory daoFactory) {
    this.userDao = daoFactory.createUserDao();
    this.taskDao = daoFactory.createTaskDao();
  }

  @Override
  public void createUserWithTasks(UserTO user, List<TaskTO> tasks) {
    User newUser = EntityFactory.createUser();
    List<Task> newTasks = new LinkedList<>();

    try {
      BeanUtils.copyProperties(newUser, user);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }

    tasks.forEach(taskTO -> {
      Task task = EntityFactory.createTask();
      try {
        BeanUtils.copyProperties(task, taskTO);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
      newTasks.add(task);
    });

    User user1 = userDao.createUser(newUser);
    taskDao.addMultipleTasks(newTasks, user1);
  }
}
