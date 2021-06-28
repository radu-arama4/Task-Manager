package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.util.OperationsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

@Component
@Qualifier("hibernate")
@Scope("singleton")
public class TaskDaoHibernate implements TaskDao {
  private final SessionFactory sessionFactory;
  private final CriteriaQuery<User> criteriaUser;
  private final CriteriaBuilder builder;
  private final CriteriaQuery<Task> criteriaTask;
  private final Root<Task> rootTask;
  private final Root<User> rootUser;
  private static final Logger logger = LogManager.getLogger(TaskDaoHibernate.class);

  public TaskDaoHibernate(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    builder = sessionFactory.getCriteriaBuilder();
    criteriaUser = builder.createQuery(User.class);
    criteriaTask = builder.createQuery(Task.class);
    rootUser = criteriaUser.from(User.class);
    rootTask = criteriaTask.from(Task.class);
  }

  @Override
  public Task addTask(Task task, User user) {
    Session session;

    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    Task newTask = new Task();

    criteriaUser.select(rootUser).where(builder.like(rootUser.get("userName"), user.getUserName()));
    Query<User> query = session.createQuery(criteriaUser);
    User selectedUser;

    try {
      selectedUser = (User) query.getSingleResult();
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
    Session session;

    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    Session finalSession = session;
    tasks.forEach(
        task -> {
          Task newTask = new Task();
          OperationsUtil.copyObjectProperties(newTask, task);
          newTask.setUser((User) user);
          finalSession.save(newTask);
        });

    return tasks;
  }

  @Override
  public Stream<Task> getTasks(User selectedUser) {
    Session session;

    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

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
