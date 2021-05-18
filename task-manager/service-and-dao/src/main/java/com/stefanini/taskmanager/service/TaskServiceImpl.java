package com.stefanini.taskmanager.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.TaskDaoImpl;

public class TaskServiceImpl implements TaskService {

    TaskDao taskDao = TaskDaoImpl.getInstance();
    private static Logger logger = LogManager.getLogger(TaskServiceImpl.class);

    @Override
    public boolean addTask(Task task, User user) {

        logger.info("addTask method started");

        String userName = user.getUserName();
        String taskTitle = task.getTaskTitle();
        String taskDescription = task.getDescription();

        if (userName == null || taskTitle == null || taskDescription == null) {
            logger.warn("Missing information!");
        } else {
            if (taskDao.addTask(new Task(taskTitle, taskDescription),
                    new User(null, null, userName)) != null) {
                logger.info("Task with [Title: " + taskTitle + "], " + "[Description: " + taskDescription
                        + "] added to user: " + userName + ".");
                return true;
            }
            logger.warn("No such user with username: " + userName);
        }
        return false;
    }

    @Override
    public List<Task> showTasks(User user) {

        String userName = user.getUserName();

        List<Task> tasks = taskDao.showTasks(new User(null, null, userName));

        if (tasks == null) {
            logger.warn("No such user with username: " + userName);
        }

        return tasks;
    }
}
