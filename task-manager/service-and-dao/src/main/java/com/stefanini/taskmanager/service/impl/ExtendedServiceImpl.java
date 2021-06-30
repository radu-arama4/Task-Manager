package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.ExtendedService;
import com.stefanini.taskmanager.util.BeansUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Scope("singleton")
public class ExtendedServiceImpl implements ExtendedService {
  @Autowired private UserDao userDao;

  @Autowired private TaskDao taskDao;

  private static final Logger logger = LogManager.getLogger(ExtendedServiceImpl.class);

  @Autowired
  public ExtendedServiceImpl() {}

  @Override
  public void createUserWithTasks(UserTO user, Stream<TaskTO> tasks) {
    User newUser = new User();
    List<Task> newTasks = new LinkedList<>();

    BeansUtil.copyObjectProperties(newUser, user);
    userDao.save(newUser);

    tasks.forEach(
        taskTO -> {
          Task task = new Task();
          BeansUtil.copyObjectProperties(task, taskTO);
          task.setUser(newUser);
          newTasks.add(task);
        });

    taskDao.save(newTasks);
    logger.info("Tasks added to user " + user.getUserName());
  }
}
