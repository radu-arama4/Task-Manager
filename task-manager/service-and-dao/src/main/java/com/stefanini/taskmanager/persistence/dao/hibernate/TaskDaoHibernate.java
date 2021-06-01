package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TaskDaoHibernate implements TaskDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);

  private static final String GET_USER_BY_USERNAME = "from User where username =: username";

  public TaskDaoHibernate(Session session) {
    this.session = session;
  }

  @Override
  public TaskTO addTask(TaskTO newTask, UserTO user) {
    Task task = new Task();
    Transaction transaction = session.beginTransaction();

    User selectedUser =
        (User)
            session
                .createQuery(GET_USER_BY_USERNAME)
                .setParameter("username", user.getUserName())
                .list()
                .get(0);

    try {
      BeanUtils.copyProperties(task, newTask);
    } catch (InvocationTargetException | IllegalAccessException e) {
      logger.error(e);
    }

    try {
      task.setUser(selectedUser);
      session.update(selectedUser);
      session.save(task);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    TaskTO returnedTask = new TaskTO();

    try {
      BeanUtils.copyProperties(returnedTask, task);
    } catch (InvocationTargetException | IllegalAccessException e) {
      logger.error(e);
    }

    return returnedTask;
  }

  @Override
  public List<TaskTO> getTasks(UserTO selectedUser) {
    User user =
        (User)
            session
                .createQuery(GET_USER_BY_USERNAME)
                .setParameter("username", selectedUser.getUserName())
                .list()
                .get(0);

    Set<Task> userTasks = user.getTasks();
    List<TaskTO> returnedTasks = new LinkedList<>();

    userTasks.forEach(
        task -> {
          TaskTO task1 = new TaskTO();
          try {
            BeanUtils.copyProperties(task1, task);
            returnedTasks.add(task1);
          } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error(e);
          }
        });

    return returnedTasks;
  }
}
