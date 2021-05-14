package com.stefanini.taskmanager.persistence.dao;

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
import com.stefanini.taskmanager.persistence.util.DButil;

public class TaskDaoImpl implements TaskDao {

  private static TaskDao singleInstance = null;
  private Connection connection = DButil.connectToDb();
  private static Logger logger = LogManager.getLogger(TaskDaoImpl.class);

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
  public Task addTask(Task task, User user) {
    String userName = user.getUserName();
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getDescription();

    String query = "INSERT INTO task(task_title, task_description) VALUES(?, ?)";
    String query2 = "INSERT INTO task_to_user(user_id, task_id)"
        + "SELECT user_id, ? FROM user WHERE user.username LIKE ?";

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
        return null;
      }

      statement = connection.prepareStatement(query2);
      statement.setLong(1, task_id);
      statement.setString(2, userName);

      int nrOfUpdates = statement.executeUpdate();

      if (nrOfUpdates == 0) {
        return null;
      }

      return new Task(task_id, taskTitle, taskDescription);
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public List<Task> showTasks(User selectedUser) {

    String userName = selectedUser.getUserName();

    final String query =
        "SELECT task.task_title, task.task_description FROM ((user JOIN task_to_user ON user.user_id = task_to_user.user_id)"
            + " JOIN task ON task_to_user.task_id = task.task_id) WHERE user.username LIKE ?";

    List<Task> tasks = new ArrayList<Task>();

    try {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, userName);

      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        String taskTitle = rs.getString("task_title");
        String description = rs.getString("task_description");

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
