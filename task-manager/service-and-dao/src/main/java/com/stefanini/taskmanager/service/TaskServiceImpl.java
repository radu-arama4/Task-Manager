package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
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
  public boolean addTask(Task task, User user) {
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
      logger.warn("No such user with username: " + userName);
    }
    return false;
  }

  @Override
  public List<Task> showTasks(User user) {
    if (user.getUserName() == null) {
      logger.warn("Missing information!");
    }

    List<Task> tasks = taskDao.showTasks(user);

    if (tasks == null) {
      logger.warn("No such user with username: " + user.getUserName());
    }

    return tasks;
  }
}
