package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.TaskService;
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
public class TaskServiceImpl implements TaskService {
  @Autowired private TaskDao taskDao;

  @Autowired private UserDao userDao;

  private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

  @Autowired
  public TaskServiceImpl() {}

  @Override
  public TaskTO addTask(TaskTO task, UserTO user) {
    logger.info("addTask method started");

    String userName = user.getUserName();
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getTaskDescription();

    Task newTask = new Task();

    if (userName == null || taskTitle == null || taskDescription == null) {
      logger.warn("Missing information!");
    } else {
      BeansUtil.copyObjectProperties(newTask, task);

      User selectedUser = userDao.findByUserName(user.getUserName());
      newTask.setUser(selectedUser);
      Task createdTask = taskDao.save(newTask);

      TaskTO returnedTask = new TaskTO();

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

        BeansUtil.copyObjectProperties(returnedTask, createdTask);
        return returnedTask;
      }
      logger.warn("No user with such username: " + userName);
    }
    return null;
  }

  @Override
  public Stream<TaskTO> getTasksOfUser(UserTO user) {
    if (user.getUserName() == null) {
      logger.warn("Missing information!");
    }

    User selectedUser = userDao.findByUserName(user.getUserName());

    List<Task> tasks = taskDao.findByUser(selectedUser);
    List<TaskTO> returnedTasks = new LinkedList<>();

    if (tasks == null) {
      logger.warn("No user with such username: " + user.getUserName());
      return null;
    }

    tasks.forEach(
        task -> {
          TaskTO task1 = new TaskTO();
          BeansUtil.copyObjectProperties(task1, task);
          returnedTasks.add(task1);
        });

    return returnedTasks.stream();
  }
}
