package com.stefanini.taskmanager.persistence.dao.jdbc;

import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.entity.EntityFactory;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@Qualifier("jdbc")
@Scope("singleton")
public class TaskDaoJdbc implements TaskDao {
  private final Connection connection = DataBaseUtil.connectWithJdbc();
  private static final Logger logger = LogManager.getLogger(TaskDaoJdbc.class);

  private static final String INSERT_INTO_TASK_QUERY =
      "INSERT INTO task(task_title, task_description) VALUES(?, ?)";
  private static final String INSERT_INTO_TASK_TO_USER_QUERY =
      "INSERT INTO task_to_user(user_id, task_id)"
          + "SELECT user_id, ? FROM user WHERE user.username = ?";
  private static final String SELECT_FROM_TASK_QUERY =
      "SELECT task.task_title, task.task_description FROM ((user JOIN task_to_user ON user.user_id = task_to_user.user_id)"
          + " JOIN task ON task_to_user.task_id = task.task_id) WHERE user.username = ?";

  public TaskDaoJdbc() {
    logger.info("TaskDao instantiated");
  }

  @Override
  public Task addTask(Task task, User user) {
    String userName = user.getUserName();
    String taskTitle = task.getTaskTitle();
    String taskDescription = task.getTaskDescription();

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

      statement = connection.prepareStatement(INSERT_INTO_TASK_TO_USER_QUERY);
      statement.setLong(1, taskId);
      statement.setString(2, userName);

      int nrOfUpdates = statement.executeUpdate();

      if (nrOfUpdates == 0) {
        return null;
      }

      return task;
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public Stream<Task> addMultipleTasks(Stream<Task> tasks, User user) {
    tasks.forEach(
        task -> addTask(task, user));
    return tasks;
  }

  @Override
  public Stream<Task> getTasks(User selectedUser) {
    String userName = selectedUser.getUserName();
    List<Task> tasks = new ArrayList<>();

    try {
      PreparedStatement statement = connection.prepareStatement(SELECT_FROM_TASK_QUERY);
      statement.setString(1, userName);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        String taskTitle = rs.getString("task_title");
        String description = rs.getString("task_description");
        Task task = EntityFactory.createTask();

        if (task != null) {
          task.setTaskTitle(taskTitle);
          task.setTaskDescription(description);
          tasks.add(task);
        }
      }
      rs.close();

    } catch (SQLException e) {
      logger.error(e);
      return null;
    }

    return tasks.stream();
  }
}
