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
    public boolean createGroup(Group group) {
        logger.info("createGroup method started");

        String groupName = null;

        if (groupDao.createGroup(new Group(groupName)) != null) {
            logger.info("New group " + groupName + " created!");
            return true;
        }

        logger.warn("Incorrect argument or already existing group!");
        return false;
    }

    @Override
    public boolean addUserToGroup(Group group, User user) {
        logger.info("addUserToGroup method started");

        String groupName = group.getGroupName();
        String userName = user.getUserName();

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
    public boolean addTaskToGroup(Group group, Task task) {
        logger.info("addTaskToGroup method started");

        String groupName = group.getGroupName();
        String taskTitle = task.getTaskTitle();
        String taskDescription = task.getDescription();

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
