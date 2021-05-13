package com.stefanini.taskmanager.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
  public boolean createGroup(Group group) {
    try {
      String groupName = group.getGroupName();
      String query = "INSERT INTO sys.group(groupName) VALUES(?)";

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, groupName);

      statement.executeUpdate();
      return true;
    } catch (SQLException e) {
      logger.error(e);
      return false;
    }
  }

  private int getIdByGroupName(String groupName) {
    try {
      String query = "SELECT group.group_Id AS group_Id FROM group WHERE group.groupName LIKE ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, groupName);

      ResultSet rs = statement.executeQuery();

      int ID = -1;

      while (rs.next()) {
        String id = rs.getString("group_Id");
        ID = Integer.parseInt(id);
        break;
      }
      return ID;

    } catch (SQLException e) {
      logger.error(e);
      return -1;
    }

  }

  @Override
  public boolean addUserToGroup(User user, Group group) {
    try {
      String groupName = group.getGroupName();
      String userName = user.getUserName();

      // TODO rename
      int ID = getIdByGroupName(groupName);

      if (ID == -1) {
        return false;
      }

      String query = "UPDATE user SET user.group_Id = ? WHERE user.userName LIKE ?";

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, ID);
      statement.setString(2, userName);
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

    int ID = getIdByGroupName(group.getGroupName());

    if (ID == -1) {
      return false;
    }
    try {
      String taskTitle = task.getTaskTitle();
      String taskDescription = task.getDescription();

      String query = "INSERT INTO task(title,description,user_Id)\r\n"
          + "SELECT ?, ?, user.user_Id FROM user WHERE user.group_Id = ?";

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, taskTitle);
      statement.setString(2, taskDescription);
      statement.setInt(3, ID);

      statement.executeUpdate();

    } catch (SQLException e) {
      logger.error(e);
      return false;
    }

    return true;
  }
}
