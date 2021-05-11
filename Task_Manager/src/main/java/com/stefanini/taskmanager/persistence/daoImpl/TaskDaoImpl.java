package com.stefanini.taskmanager.persistence.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.util.DButil;

public class TaskDaoImpl implements TaskDao {

  private static TaskDaoImpl singleInstance = null;
  private Connection connection = null;
  private static Logger logger = LogManager.getLogger(GroupDaoImpl.class);

  private TaskDaoImpl() {
    connection = DButil.connectToDb();
  }

  static public TaskDaoImpl getInstance() {
    if (singleInstance == null) {
      singleInstance = new TaskDaoImpl();
      logger.info("TaskDao instantiated");
    }
    return singleInstance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addTask(Task task, User user) {
    try {
      String userName = user.getUserName();
      String taskTitle = task.getTaskTitle();
      String taskDescription = task.getDescription();

      String query = "INSERT INTO sys.task(title, description, user_Id) "
          + "SELECT ?, ?, sys.user.user_Id FROM sys.user " + "WHERE sys.user.userName LIKE ?";

      PreparedStatement pStatement = connection.prepareStatement(query);
      pStatement.setString(1, taskTitle);
      pStatement.setString(2, taskDescription);
      pStatement.setString(3, userName);

      int c = pStatement.executeUpdate();

      if (c == 0) {
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
  public List<Task> showTasks(User selectedUser) throws SQLException {

    String userName = selectedUser.getUserName();

    final String query = "SELECT sys.task.title, sys.task.description \r\n"
        + "FROM sys.user JOIN sys.task on sys.user.user_Id=sys.task.user_Id\r\n"
        + "WHERE sys.user.userName like ?";

    PreparedStatement pStatement = connection.prepareStatement(query);
    pStatement.setString(1, userName);

    ResultSet rs = pStatement.executeQuery();

    List<Task> tasks = new ArrayList<Task>();

    while (rs.next()) {
      String taskTitle = rs.getString("title");
      String description = rs.getString("description");

      System.out.println(taskTitle + " " + description);

      tasks.add(new Task(taskTitle, description));
    }

    rs.close();
    return tasks;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void finalize() throws Throwable {
    DButil.disconnectFromDb();
  }

}
