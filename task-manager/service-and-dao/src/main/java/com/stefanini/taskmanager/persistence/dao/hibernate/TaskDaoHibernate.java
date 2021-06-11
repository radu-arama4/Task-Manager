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
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TaskDaoHibernate implements TaskDao {
  private final Session session;
  private final CriteriaQuery<User> criteriaUser;
  private final CriteriaBuilder builder;
  private final CriteriaQuery<Task> criteriaTask;
  private final Root<TaskHibernate> rootTask;
  private final Root<UserHibernate> rootUser;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);
  private static TaskDao singleInstance;

  private TaskDaoHibernate(Session session) {
    this.session = session;
    builder = session.getCriteriaBuilder();
    criteriaUser = builder.createQuery(User.class);
    criteriaTask = builder.createQuery(Task.class);
    rootUser = criteriaUser.from(UserHibernate.class);
    rootTask = criteriaTask.from(TaskHibernate.class);
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

    criteriaUser.select(rootUser).where(builder.like(rootUser.get("userName"), user.getUserName()));
    Query<User> query = session.createQuery(criteriaUser);
    UserHibernate selectedUser;

    try {
      selectedUser = (UserHibernate) query.getSingleResult();
    } catch (NoResultException e) {
      logger.error(e);
      return null;
    }

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
    criteriaTask
        .select(rootTask)
        .where(builder.like(rootTask.get("user").get("userName"), selectedUser.getUserName()));

    Query<Task> query = session.createQuery(criteriaTask);

    try {
      return query.getResultList();
    } catch (NoResultException e) {
      logger.error(e);
      return null;
    }
  }
}
