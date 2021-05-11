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

  private static GroupDaoImpl singleInstance = null;
  private Connection connection = DButil.connectToDb();
  private static Logger logger = LogManager.getLogger(GroupDaoImpl.class);

  private GroupDaoImpl() {
    logger.info("GroupDao instantiated");
  }

  static public GroupDaoImpl getInstance() {
    if (singleInstance == null) {
      singleInstance = new GroupDaoImpl();
    }
    return singleInstance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean createGroup(Group group) {
    try {
      String groupName = group.getGroupName();
      String query = "INSERT INTO sys.group(groupName) " + "VALUES(?)";

      PreparedStatement pStatement = connection.prepareStatement(query);
      pStatement.setString(1, groupName);

      pStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  private int getIdByGroupName(String groupName) {
    try {
      String query = "select sys.group.group_Id as group_Id \r\n" + "from sys.group\r\n"
          + "where sys.group.groupName LIKE ?;";
      PreparedStatement pStatement = connection.prepareStatement(query);
      pStatement.setString(1, groupName);

      ResultSet rs = pStatement.executeQuery();

      int ID = -1;

      while (rs.next()) {
        String id = rs.getString("group_Id");
        ID = Integer.parseInt(id);
        break;
      }
      return ID;

    } catch (SQLException e) {
      logger.error(e.getMessage());
      return -1;
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addUserToGroup(User user, Group group) {
    try {
      String groupName = group.getGroupName();
      String userName = user.getUserName();

      int ID = getIdByGroupName(groupName);

      if (ID == -1) {
        return false;
      }

      String query = "UPDATE sys.user SET sys.user.group_Id = ? WHERE sys.user.userName LIKE ?;";

      PreparedStatement pStatement = connection.prepareStatement(query);
      pStatement.setInt(1, ID);
      pStatement.setString(2, userName);
      int nrOfUpdates = pStatement.executeUpdate();

      if (nrOfUpdates == 0) {
        return false;
      }

      return true;
    } catch (SQLException e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addTaskToGroup(Task task, Group group) {

    int ID = getIdByGroupName(group.getGroupName());

    if (ID == -1) {
      return false;
    }
    try {
      String taskTitle = task.getTaskTitle();
      String taskDescription = task.getDescription();

      String query = "INSERT INTO sys.task(title,description,user_Id)\r\n"
          + "SELECT ?, ?, sys.user.user_Id\r\n" + "FROM sys.user WHERE sys.user.group_Id = ?;";

      PreparedStatement pStatement = connection.prepareStatement(query);
      pStatement.setString(1, taskTitle);
      pStatement.setString(2, taskDescription);
      pStatement.setInt(3, ID);

      pStatement.executeUpdate();

    } catch (SQLException e) {
      logger.error(e.getMessage());
      return false;
    }

    return true;
  }
}
