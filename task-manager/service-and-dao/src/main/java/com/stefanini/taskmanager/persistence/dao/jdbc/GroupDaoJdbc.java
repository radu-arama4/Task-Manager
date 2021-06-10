package com.stefanini.taskmanager.persistence.dao.jdbc;

import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class GroupDaoJdbc implements GroupDao {
  private static GroupDao singleInstance = null;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);

  private static final String CREATE_GROUP_QUERY = "INSERT INTO `group_`(`group_name`) VALUES(?)";
  private static final String ADD_USER_TO_GROUP_QUERY =
      "INSERT INTO `user_to_group` (`user_id`, `group_id`) "
          + "SELECT (SELECT user.user_id FROM `user` "
          + "WHERE user.username = ?) AS `user_id`, (SELECT group_.group_id FROM `group_` "
          + "WHERE group_.group_name = ?) as `group_id`";
  private static final String INSERT_INTO_TASK_QUERY =
      "INSERT INTO task(task_title, task_description) VALUES(?, ?)";
  private static final String INSERT_INTO_TASK_TO_GROUP_QUERY =
      "INSERT INTO `task_to_group` (`task_id`, `group_id`)"
          + "SELECT ?, group_id FROM `group_` WHERE group_.group_name LIKE ?";

  private GroupDaoJdbc(Connection connection) {
    this.connection = connection;
    logger.info("GroupDao instantiated");
  }

  public static GroupDao getInstance(Connection connection) {
    if (singleInstance == null) {
      singleInstance = new GroupDaoJdbc(connection);
    }
    return singleInstance;
  }

  @Override
  public Group createGroup(Group group) {
    try {
      String groupName = group.getGroupName();

      PreparedStatement statement =
          connection.prepareStatement(CREATE_GROUP_QUERY, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, groupName);
      statement.executeUpdate();

      ResultSet rs = statement.getGeneratedKeys();
      long groupId;

      if (rs.next()) {
        groupId = rs.getLong(1);
        group.setGroupId(groupId);
      } else {
        return null;
      }

      return group;
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public User addUserToGroup(User user, Group group) {
    try {
      String groupName = group.getGroupName();
      String userName = user.getUserName();

      PreparedStatement statement = connection.prepareStatement(ADD_USER_TO_GROUP_QUERY);
      statement.setString(1, userName);
      statement.setString(2, groupName);
      int nrOfUpdates = statement.executeUpdate();

      if (nrOfUpdates != 0) {
        return user;
      } else {
        return null;
      }
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public Task addTaskToGroup(Task task, Group group) {
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getTaskDescription();
    String groupName = group.getGroupName();

    try {
      PreparedStatement statement =
          connection.prepareStatement(INSERT_INTO_TASK_QUERY, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, taskTitle);
      statement.setString(2, taskDescription);

      statement.executeUpdate();

      ResultSet rs = statement.getGeneratedKeys();
      long taskId;

      if (rs.next()) {
        taskId = rs.getLong(1);
        task.setTaskId(taskId);
      } else {
        return null;
      }

      statement = connection.prepareStatement(INSERT_INTO_TASK_TO_GROUP_QUERY);
      statement.setLong(1, taskId);
      statement.setString(2, groupName);

      int nrOfUpdates = statement.executeUpdate();

      if (nrOfUpdates != 0) {
        return task;
      } else {
        return null;
      }
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }
}
