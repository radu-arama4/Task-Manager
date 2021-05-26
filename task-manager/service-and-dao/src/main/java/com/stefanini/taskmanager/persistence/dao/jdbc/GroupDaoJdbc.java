package com.stefanini.taskmanager.persistence.dao.jdbc;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.GroupDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class GroupDaoJdbc implements GroupDao {
  private static GroupDao singleInstance = null;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);

  private static final String CREATE_GROUP_QUERY = "INSERT INTO `group`(`group_name`) VALUES(?)";
  private static final String ADD_USER_TO_GROUP_QUERY =
      "INSERT INTO `user_to_group` (`user_id`, `group_id`) "
          + "SELECT (SELECT user.user_id FROM `user` "
          + "WHERE user.username = ?) AS `user_id`, (SELECT group.group_id FROM `group` "
          + "WHERE group.group_name = ?) as `group_id`";
  private static final String INSERT_INTO_TASK_QUERY =
      "INSERT INTO task(task_title, task_description) VALUES(?, ?)";
  private static final String INSERT_INTO_TASK_TO_GROUP_QUERY =
      "INSERT INTO `task_to_group` (`task_id`, `group_id`)"
          + "SELECT ?, group_id FROM `group` WHERE group.group_name LIKE ?";

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
  public GroupTO createGroup(GroupTO group) {
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
      } else {
        return null;
      }

      return new GroupTO(groupId, groupName);
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public boolean addUserToGroup(UserTO user, GroupTO group) {
    try {
      String groupName = group.getGroupName();
      String userName = user.getUserName();

      PreparedStatement statement = connection.prepareStatement(ADD_USER_TO_GROUP_QUERY);
      statement.setString(1, userName);
      statement.setString(2, groupName);
      int nrOfUpdates = statement.executeUpdate();

      return nrOfUpdates != 0;
    } catch (SQLException e) {
      logger.error(e);
      return false;
    }
  }

  @Override
  public boolean addTaskToGroup(TaskTO task, GroupTO group) {
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getDescription();
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
      } else {
        return false;
      }

      statement = connection.prepareStatement(INSERT_INTO_TASK_TO_GROUP_QUERY);
      statement.setLong(1, taskId);
      statement.setString(2, groupName);

      int nrOfUpdates = statement.executeUpdate();

      return nrOfUpdates != 0;
    } catch (SQLException e) {
      logger.error(e);
      return false;
    }
  }
}
