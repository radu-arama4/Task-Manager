package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TaskServiceImpl implements TaskService {
  private final TaskDao taskDao;
  private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

  public TaskServiceImpl(DaoFactory daoFactory) {
    this.taskDao = daoFactory.createTaskDao();
  }

  @Override
  public boolean addTask(TaskTO task, UserTO user) {
    logger.info("addTask method started");

    String userName = user.getUserName();
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getDescription();

    if (userName == null || taskTitle == null || taskDescription == null) {
      logger.warn("Missing information!");
    } else {
      if (taskDao.addTask(task, user) != null) {
        logger.info(
            "Task with [Title: "
                + taskTitle
                + "], "
                + "[Description: "
                + taskDescription
                + "] added to user: "
                + userName
                + ".");
        return true;
      }
      logger.warn("No user with such username: " + userName);
    }
    return false;
  }

  @Override
  public boolean addMultipleTasks(List<TaskTO> tasks, UserTO user) {
    String userName = user.getUserName();

    if (tasks.size() == 0 || userName == null) {
      logger.warn("Missing information!");
    } else {
      if (taskDao.addMultipleTasks(tasks, user) != null) {
        logger.info(tasks.size() + " tasks added to user: " + userName);
        return true;
      } else {
        logger.warn("No user with such username: " + userName);
      }
    }
    return false;
  }

  @Override
  public List<TaskTO> getTasksOfUser(UserTO user) {
    if (user.getUserName() == null) {
      logger.warn("Missing information!");
    }

    List<TaskTO> tasks = taskDao.getTasks(user);

    if (tasks == null) {
      logger.warn("No user with such username: " + user.getUserName());
    }

    return tasks;
  }
}
