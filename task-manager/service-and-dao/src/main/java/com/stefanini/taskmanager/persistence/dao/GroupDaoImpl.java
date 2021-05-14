package com.stefanini.taskmanager.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.util.DButil;

public class GroupDaoImpl implements GroupDao {

  private static GroupDao singleInstance = null;
  private Connection connection = DButil.connectToDb();
  private static Logger logger = LogManager.getLogger(GroupDaoImpl.class);

  private GroupDaoImpl() {
    logger.info("GroupDao instantiated");
  }

  static public GroupDao getInstance() {
    if (singleInstance == null) {
      singleInstance = new GroupDaoImpl();
    }
    return singleInstance;
  }

  @Override
  public Group createGroup(Group group) {
    try {
      String groupName = group.getGroupName();
      String query = "INSERT INTO group(group_name) VALUES(?)";

      PreparedStatement statement =
          connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, groupName);

      statement.executeUpdate();

      ResultSet rs = statement.getGeneratedKeys();
      Long group_id = null;

      if (rs.next()) {
        group_id = rs.getLong(1);
      } else {
        return null;
      }

      return new Group(group_id, groupName);
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public boolean addUserToGroup(User user, Group group) {
    try {
      String groupName = group.getGroupName();
      String userName = user.getUserName();

      String query =
          "INSERT INTO user_to_group (user_id, group_id) SELECT (SELECT user.user_id FROM user"
              + "WHERE user.username LIKE ?) AS user_id, (SELECT group.group_id FROM group"
              + "WHERE group.group_name LIKE ?) as group_id";

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, userName);
      statement.setString(2, groupName);
      int nrOfUpdates = statement.executeUpdate();

      if (nrOfUpdates == 0) {
        return false;
      }

      return true;
    } catch (SQLException e) {
      logger.error(e);
      return false;
    }
  }

  @Override
  public boolean addTaskToGroup(Task task, Group group) {

    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getDescription();
    String groupName = group.getGroupName();

    String query = "INSERT INTO task(task_title, task_description) VALUES(?, ?)";
    String query2 = "INSERT INTO task_to_group(task_id, group_id)"
        + "SELECT ?, group_id FROM group WHERE group.group_name LIKE ?";

    try {
      PreparedStatement statement =
          connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, taskTitle);
      statement.setString(2, taskDescription);

      statement.executeUpdate();

      ResultSet rs = statement.getGeneratedKeys();
      Long task_id = null;

      if (rs.next()) {
        task_id = rs.getLong(1);
      } else {
        return false;
      }

      statement = connection.prepareStatement(query2);
      statement.setLong(1, task_id);
      statement.setString(2, groupName);

      int nrOfUpdates = statement.executeUpdate();

      if (nrOfUpdates == 0) {
        return false;
      }

      return true;
    } catch (SQLException e) {
      logger.error(e);
      return false;
    }

  }
}
