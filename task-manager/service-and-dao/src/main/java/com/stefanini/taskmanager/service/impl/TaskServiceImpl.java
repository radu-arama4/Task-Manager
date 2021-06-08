package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.entity.EntityFactory;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.TaskService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class TaskServiceImpl implements TaskService {
  private final TaskDao taskDao;
  private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

  public TaskServiceImpl(DaoFactory daoFactory) {
    this.taskDao = daoFactory.createTaskDao();
  }

  @Override
  public TaskTO addTask(TaskTO task, UserTO user) {
    logger.info("addTask method started");

    String userName = user.getUserName();
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getTaskDescription();

    User newUser;
    Task newTask;

    if (userName == null || taskTitle == null || taskDescription == null) {
      logger.warn("Missing information!");
    } else {
      newUser = EntityFactory.createUser();
      newTask = EntityFactory.createTask();
      try {
        BeanUtils.copyProperties(newUser, user);
        BeanUtils.copyProperties(newTask, task);
      } catch (InvocationTargetException | IllegalAccessException e) {
        logger.error(e);
      }

      TaskTO returnedTask = new TaskTO();
      Task createdTask = taskDao.addTask(newTask, newUser);

      if (createdTask != null) {
        logger.info(
            "Task with [Title: "
                + taskTitle
                + "], "
                + "[Description: "
                + taskDescription
                + "] added to user: "
                + userName
                + ".");
        try {
          BeanUtils.copyProperties(returnedTask, createdTask);
        } catch (InvocationTargetException | IllegalAccessException e) {
          logger.error(e);
        }
        return returnedTask;
      }
      logger.warn("No user with such username: " + userName);
    }
    return null;
  }

  @Override
  public List<TaskTO> getTasksOfUser(UserTO user) {
    if (user.getUserName() == null) {
      logger.warn("Missing information!");
    }

    User selectedUser = EntityFactory.createUser();

    try {
      BeanUtils.copyProperties(selectedUser, user);
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }

    List<Task> tasks = taskDao.getTasks(selectedUser);
    List<TaskTO> returnedTasks = new LinkedList<>();

    if (tasks == null) {
      logger.warn("No user with such username: " + user.getUserName());
      return null;
    }

    tasks.forEach(
        task -> {
          TaskTO task1 = new TaskTO();
          try {
            BeanUtils.copyProperties(task1, task);
          } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error(e);
          }
          returnedTasks.add(task1);
        });

    return returnedTasks;
  }
}
