package com.stefanini.taskmanager.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.util.DButil;

public class TaskDaoImpl implements TaskDao {

  private static TaskDaoImpl singleInstance = null;
  private Connection connection = DButil.connectToDb();
  private static Logger logger = LogManager.getLogger(GroupDaoImpl.class);

  private TaskDaoImpl() {
    logger.info("TaskDao instantiated");
  }

  static public TaskDaoImpl getInstance() {
    if (singleInstance == null) {
      singleInstance = new TaskDaoImpl();
    }
    return singleInstance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addTask(Task task, User user) {
    String userName = user.getUserName();
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getDescription();

    String query = "INSERT INTO sys.task(title, description, user_Id) "
        + "SELECT ?, ?, sys.user.user_Id FROM sys.user WHERE sys.user.userName LIKE ?";

    try {
      PreparedStatement pStatement = connection.prepareStatement(query);
      pStatement.setString(1, taskTitle);
      pStatement.setString(2, taskDescription);
      pStatement.setString(3, userName);

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
  public List<Task> showTasks(User selectedUser) {

    String userName = selectedUser.getUserName();

    final String query = "SELECT sys.task.title, sys.task.description \r\n"
        + "FROM sys.user JOIN sys.task on sys.user.user_Id=sys.task.user_Id\r\n"
        + "WHERE sys.user.userName like ?";

    List<Task> tasks = new ArrayList<Task>();

    try {
      PreparedStatement pStatement = connection.prepareStatement(query);
      pStatement.setString(1, userName);

      ResultSet rs = pStatement.executeQuery();

      while (rs.next()) {
        String taskTitle = rs.getString("title");
        String description = rs.getString("description");

        System.out.println(taskTitle + " " + description);

        tasks.add(new Task(taskTitle, description));
        rs.close();
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
      return null;
    }

    return tasks;
  }
}
