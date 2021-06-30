package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.util.BeansUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class GroupServiceImpl implements GroupService {
  @Autowired private GroupDao groupDao;

  @Autowired private UserDao userDao;

  @Autowired private TaskDao taskDao;

  private static final Logger logger = LogManager.getLogger(GroupServiceImpl.class);

  @Autowired
  public GroupServiceImpl() {}

  @Override
  public boolean createGroup(GroupTO group) {
    logger.info("createGroup method started");

    if (group.getGroupName() == null) {
      return false;
    }

    Group newGroup = new Group();
    BeansUtil.copyObjectProperties(newGroup, group);

    if (groupDao.save(newGroup) != null) {
      logger.info("New group " + group.getGroupName() + " created!");
      return true;
    }

    logger.warn("Incorrect argument or already existing group!");
    return false;
  }

  @Override
  public boolean addUserToGroup(GroupTO group, UserTO user) {
    logger.info("addUserToGroup method started");

    String groupName = group.getGroupName();
    String userName = user.getUserName();

    if (groupName != null || userName != null) {
      Group selectedGroup = groupDao.findByGroupName(groupName);
      User selectedUser = userDao.findByUserName(userName);

      selectedGroup.addUser(selectedUser);

      if (groupDao.save(selectedGroup) != null) {
        logger.info("User " + userName + " added to group " + groupName);
        return true;
      }
    } else {
      logger.warn("Incorrect information!");
    }
    return false;
  }

  @Override
  public boolean addTaskToGroup(GroupTO group, TaskTO task) {
    logger.info("addTaskToGroup method started");

    String groupName = group.getGroupName();
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getTaskDescription();

    if (groupName == null || taskTitle == null || taskDescription == null) {
      logger.warn("Information missing!");
    } else {
      Group selectedGroup = groupDao.findByGroupName(groupName);

      Task selectedTask = new Task();
      BeansUtil.copyObjectProperties(selectedTask, task);
      selectedTask.setGroup(selectedGroup);

      if (taskDao.save(selectedTask) != null) {
        logger.info(
            "Task with [title: "
                + taskTitle
                + "], [description: "
                + taskDescription
                + "] added to group "
                + groupName);
        return true;
      }
      logger.warn("No such group!");
    }
    return false;
  }
}
