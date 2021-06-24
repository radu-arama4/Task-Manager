package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.entity.EntityFactory;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.TaskService;
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
public class TaskServiceImpl implements TaskService {
  @Autowired
  @Qualifier("hibernate")
  private TaskDao taskDao;
  private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

  @Autowired
  public TaskServiceImpl() {
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

      OperationsUtil.copyObjectProperties(newUser, user);
      OperationsUtil.copyObjectProperties(newTask, task);

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

        OperationsUtil.copyObjectProperties(returnedTask, createdTask);

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

    User selectedUser = EntityFactory.createUser();

    OperationsUtil.copyObjectProperties(selectedUser, user);

    Stream<Task> tasks = taskDao.getTasks(selectedUser);
    List<TaskTO> returnedTasks = new LinkedList<>();

    if (tasks == null) {
      logger.warn("No user with such username: " + user.getUserName());
      return null;
    }

    tasks.forEach(
        task -> {
          TaskTO task1 = new TaskTO();
          OperationsUtil.copyObjectProperties(task1, task);
          returnedTasks.add(task1);
        });

    return returnedTasks.stream();
  }
}
