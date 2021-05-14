package com.stefanini.taskmanager.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.GroupDaoImpl;

public class GroupServiceImpl implements GroupService {

  private GroupDao groupDao = GroupDaoImpl.getInstance();
  private static Logger logger = LogManager.getLogger(GroupServiceImpl.class);

  @Override
  public boolean createGroup(String[] arguments) {
    logger.info("createGroup method started");

    if (arguments.length > 2) {
      logger.warn("Too many arguments");
      return false;
    }

    String groupName = null;

    if (arguments[1].startsWith("-gn='") && arguments[1].endsWith("'")) {
      groupName = arguments[1].substring(5, arguments[1].length() - 1);
      if (groupDao.createGroup(new Group(groupName)) != null) {
        logger.info("New group " + groupName + " created!");
        return true;
      }
    }
    logger.warn("Incorrect argument or already existing group!");
    return false;
  }

  @Override
  public boolean addUserToGroup(String[] arguments) {
    logger.info("addUserToGroup method started");

    if (arguments.length > 3) {
      logger.warn("Too many arguments");
      return false;
    }

    String groupName = null;
    String userName = null;

    for (String arg : arguments) {
      if (arg.startsWith("-gn='") && arg.endsWith("'")) {
        groupName = arg.substring(5, arg.length() - 1);
      } else if (arg.startsWith("-un='") && arg.endsWith("'")) {
        userName = arg.substring(5, arg.length() - 1);
      }
    }

    if (groupName != null || userName != null) {

      if (groupDao.addUserToGroup(new User(null, null, userName), new Group(groupName))) {
        logger.info("User " + userName + " added to group " + groupName);
        return true;
      }
      logger.warn("No such group or user!");
    } else {
      logger.warn("Missing information!");
    }
    return false;
  }

  @Override
  public boolean addTaskToGroup(String[] arguments) {
    logger.info("addTaskToGroup method started");

    String groupName = null;
    String taskTitle = null;
    String taskDescription = null;

    StringBuilder taskT = new StringBuilder();
    StringBuilder taskD = new StringBuilder();
    String prev = "";

    int count = 0;

    for (String arg : arguments) {
      if (arg.startsWith("-gn='") && arg.endsWith("'")) {
        groupName = arg.substring(5, arg.length() - 1);
        count++;
      } else if (arg.startsWith("-tt='") && arg.endsWith("'")) {
        taskTitle = arg.substring(5, arg.length() - 1);
        count++;
      } else if (arg.startsWith("-tt='")) {
        prev = "-tt='";
        taskT.append(arg, 5, arg.length());
        taskT.append(" ");
        count++;
      } else if (prev.equals("-tt='")) {
        if (arg.endsWith("'")) {
          taskT.append(arg, 0, arg.length() - 1);
          taskTitle = taskT.toString();
          prev = "";
        } else {
          taskT.append(arg).append(" ");
        }
      } else if (arg.startsWith("-td='") && arg.endsWith("'")) {
        taskDescription = arg.substring(5, arg.length() - 1);
        count++;
      } else if (arg.startsWith("-td='")) {
        prev = "-td='";
        taskD.append(arg, 5, arg.length());
        taskD.append(" ");
        count++;
      } else if (prev.equals("-td='")) {
        if (arg.endsWith("'")) {
          taskD.append(arg, 0, arg.length() - 1);
          taskDescription = taskD.toString();
          prev = "";
        } else {
          taskD.append(arg).append(" ");
        }
      }
    }

    if (count > 3) {
      logger.warn("Too many arguments");
      return false;
    }

    System.out.println(groupName + " " + taskTitle + " " + taskDescription);

    if (groupName == null || taskTitle == null || taskDescription == null) {
      logger.warn("Information missing!");
    } else {
      if (groupDao.addTaskToGroup(new Task(taskTitle, taskDescription), new Group(groupName))) {
        logger.info("Task with [title: " + taskTitle + "], [description: " + taskDescription
            + "] added to group " + groupName);
        return true;
      }
      logger.warn("No such group!");
    }
    return false;
  }

}
