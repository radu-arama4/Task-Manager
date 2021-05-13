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

  private static TaskDao singleInstance = null;
  private Connection connection = DButil.connectToDb();
  private static Logger logger = LogManager.getLogger(GroupDaoImpl.class);

  private TaskDaoImpl() {
    logger.info("TaskDao instantiated");
  }

  static public TaskDao getInstance() {
    if (singleInstance == null) {
      singleInstance = new TaskDaoImpl();
    }
    return singleInstance;
  }

  @Override
  public boolean addTask(Task task, User user) {
    String userName = user.getUserName();
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getDescription();

    String query = "INSERT INTO task(title, description, user_Id) "
        + "SELECT ?, ?, user.user_Id FROM user WHERE user.userName LIKE ?";

    try {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, taskTitle);
      statement.setString(2, taskDescription);
      statement.setString(3, userName);

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
  public List<Task> showTasks(User selectedUser) {

    String userName = selectedUser.getUserName();

    final String query = "SELECT task.title, task.description \r\n"
        + "FROM user JOIN task on user.user_Id=task.user_Id WHERE user.userName like ?";

    List<Task> tasks = new ArrayList<Task>();

    try {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, userName);

      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        String taskTitle = rs.getString("title");
        String description = rs.getString("description");

        logger.info(taskTitle + " " + description);

        tasks.add(new Task(taskTitle, description));
      }

      rs.close();

    } catch (SQLException e) {
      logger.error(e);
      return null;
    }

    return tasks;
  }
}
