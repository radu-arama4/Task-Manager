package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.entity.hibernate.TaskHibernate;
import com.stefanini.taskmanager.persistence.entity.hibernate.UserHibernate;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TaskDaoHibernate implements TaskDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);
  private static TaskDao singleInstance;

  private static final String GET_USER_BY_USERNAME = "from User where username =: username";
  private static final String GET_TASKS_BY_USER_USERNAME =
      "from Task t where t.user.userName =: username";

  private TaskDaoHibernate(Session session) {
    this.session = session;
  }

  public static TaskDao getInstance(Session session) {
    if (singleInstance == null) {
      singleInstance = new TaskDaoHibernate(session);
    }
    return singleInstance;
  }

  @Override
  public Task addTask(Task task, User user) {
    TaskHibernate newTask = new TaskHibernate();

    UserHibernate selectedUser =
        (UserHibernate)
            session
                .createQuery(GET_USER_BY_USERNAME)
                .setParameter("username", user.getUserName())
                .getSingleResult();

    copyTasks(newTask, task);

    try {
      newTask.setUser(selectedUser);
      session.save(newTask);
    } catch (Exception e) {
      logger.error(e);
      return null;
    }

    return newTask;
  }

  private void copyTasks(Task task1, Task task2) {
    try {
      BeanUtils.copyProperties(task1, task2);
    } catch (InvocationTargetException | IllegalAccessException e) {
      logger.error(e);
    }
  }

  @Override
  public List<Task> addMultipleTasks(List<Task> tasks, User user) {
    tasks.forEach(
        task -> {
          TaskHibernate newTask = new TaskHibernate();
          copyTasks(newTask, task);
          newTask.setUser((UserHibernate) user);
          session.save(newTask);
        });

    return tasks;
  }

  @Override
  public List<Task> getTasks(User selectedUser) {
    return (List<Task>)
        session
            .createQuery(GET_TASKS_BY_USER_USERNAME)
            .setParameter("username", selectedUser.getUserName())
            .list();
  }
}
