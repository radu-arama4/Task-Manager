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
import org.hibernate.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TaskDaoHibernate implements TaskDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);

  private static final String GET_USER_BY_USERNAME = "from User where username =: username";
  private static final String GET_TASKS_BY_USER_USERNAME =
      "from Task t where t.user.userName =: username";

  public TaskDaoHibernate(Session session) {
    this.session = session;
  }

  @Override
  public Task addTask(Task newTask, User user) {
    TaskHibernate task = new TaskHibernate();
    Transaction transaction = session.beginTransaction();

    UserHibernate selectedUser =
        (UserHibernate)
            session
                .createQuery(GET_USER_BY_USERNAME)
                .setParameter("username", user.getUserName())
                .getSingleResult();

    try {
      BeanUtils.copyProperties(task, newTask);
    } catch (InvocationTargetException | IllegalAccessException e) {
      logger.error(e);
    }

    try {
      task.setUser(selectedUser);
      session.save(task);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    return task;
  }

  @Override
  public List<Task> addMultipleTasks(List<Task> tasks, User user) {
    tasks.forEach(
        taskTO -> {
          TaskHibernate task = new TaskHibernate();
          try {
            BeanUtils.copyProperties(task, taskTO);
            task.setUser((UserHibernate) user);
            session.save(task);
          } catch (InvocationTargetException | IllegalAccessException e) {
            session.getTransaction().rollback();
            logger.error(e);
          }
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
