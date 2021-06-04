package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.ExtendedService;

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
    User user1 = userDao.createUser(user);
    taskDao.addMultipleTasks(tasks, user1);
  }
}
