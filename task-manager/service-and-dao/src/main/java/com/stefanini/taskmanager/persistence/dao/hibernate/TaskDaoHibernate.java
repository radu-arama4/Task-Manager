package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.entity.hibernate.TaskHibernate;
import com.stefanini.taskmanager.persistence.entity.hibernate.UserHibernate;
import com.stefanini.taskmanager.util.OperationsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

public class TaskDaoHibernate implements TaskDao {
  private final SessionFactory sessionFactory;
  private final CriteriaQuery<User> criteriaUser;
  private final CriteriaBuilder builder;
  private final CriteriaQuery<Task> criteriaTask;
  private final Root<TaskHibernate> rootTask;
  private final Root<UserHibernate> rootUser;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);
  private static TaskDao singleInstance;

  private TaskDaoHibernate(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    builder = sessionFactory.getCriteriaBuilder();
    criteriaUser = builder.createQuery(User.class);
    criteriaTask = builder.createQuery(Task.class);
    rootUser = criteriaUser.from(UserHibernate.class);
    rootTask = criteriaTask.from(TaskHibernate.class);
  }

  public static TaskDao getInstance(SessionFactory sessionFactory) {
    if (singleInstance == null) {
      singleInstance = new TaskDaoHibernate(sessionFactory);
    }
    return singleInstance;
  }

  @Override
  public Task addTask(Task task, User user) {
    Session session = sessionFactory.getCurrentSession();
    TaskHibernate newTask = new TaskHibernate();

    criteriaUser.select(rootUser).where(builder.like(rootUser.get("userName"), user.getUserName()));
    Query<User> query = session.createQuery(criteriaUser);
    UserHibernate selectedUser;

    try {
      selectedUser = (UserHibernate) query.getSingleResult();
    } catch (NoResultException e) {
      logger.error(e);
      return null;
    }

    OperationsUtil.copyObjectProperties(newTask, task);

    try {
      newTask.setUser(selectedUser);
      session.save(newTask);
    } catch (Exception e) {
      logger.error(e);
      return null;
    }
    return newTask;
  }

  @Override
  public Stream<Task> addMultipleTasks(Stream<Task> tasks, User user) {
    Session session = sessionFactory.getCurrentSession();
    tasks.forEach(
        task -> {
          TaskHibernate newTask = new TaskHibernate();
          OperationsUtil.copyObjectProperties(newTask, task);
          newTask.setUser((UserHibernate) user);
          session.save(newTask);
        });

    return tasks;
  }

  @Override
  public Stream<Task> getTasks(User selectedUser) {
    Session session = sessionFactory.getCurrentSession();
    criteriaTask
        .select(rootTask)
        .where(builder.like(rootTask.get("user").get("userName"), selectedUser.getUserName()));

    Query<Task> query = session.createQuery(criteriaTask);

    try {
      return query.getResultList().stream();
    } catch (NoResultException e) {
      logger.error(e);
      return null;
    }
  }
}
