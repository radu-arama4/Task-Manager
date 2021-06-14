package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.entity.hibernate.GroupHibernate;
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

public class GroupDaoHibernate implements GroupDao {
  private final SessionFactory sessionFactory;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);
  private static GroupDao singleInstance;
  private final CriteriaQuery<User> criteriaUser;
  private final CriteriaQuery<Task> criteriaTask;
  private final CriteriaQuery<Group> criteriaGroup;
  private final CriteriaBuilder builder;
  private final Root<TaskHibernate> rootTask;
  private final Root<GroupHibernate> rootGroup;
  private final Root<UserHibernate> rootUser;

  private GroupDaoHibernate(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    builder = sessionFactory.getCriteriaBuilder();
    criteriaUser = builder.createQuery(User.class);
    criteriaTask = builder.createQuery(Task.class);
    criteriaGroup = builder.createQuery(Group.class);
    rootUser = criteriaUser.from(UserHibernate.class);
    rootTask = criteriaTask.from(TaskHibernate.class);
    rootGroup = criteriaGroup.from(GroupHibernate.class);
  }

  public static GroupDao getInstance(SessionFactory sessionFactory) {
    if (singleInstance == null) {
      singleInstance = new GroupDaoHibernate(sessionFactory);
    }
    return singleInstance;
  }

  @Override
  public Group createGroup(Group group) {
    Session session = sessionFactory.openSession();
    try {
      session.save(group);
    } catch (Exception e) {
      logger.error(e);
      return null;
    }
    session.close();
    return group;
  }

  @Override
  public User addUserToGroup(User user, Group group) {
    Session session = sessionFactory.openSession();
    UserHibernate selectedUser;
    GroupHibernate selectedGroup;

    criteriaUser.select(rootUser).where(builder.like(rootUser.get("userName"), user.getUserName()));
    criteriaGroup
        .select(rootGroup)
        .where(builder.like(rootGroup.get("groupName"), group.getGroupName()));

    Query<User> queryUser = session.createQuery(criteriaUser);
    Query<Group> queryGroup = session.createQuery(criteriaGroup);

    try {
      selectedUser = (UserHibernate) queryUser.getSingleResult();
      selectedGroup = (GroupHibernate) queryGroup.getSingleResult();
    } catch (NoResultException e) {
      logger.error(e);
      session.close();
      return null;
    }

    try {
      selectedGroup.addUser(selectedUser);
    } catch (Exception e) {
      logger.error(e);
      session.close();
      return null;
    }

    session.close();
    return selectedUser;
  }

  @Override
  public Task addTaskToGroup(Task newTask, Group group) {
    Session session = sessionFactory.openSession();
    TaskHibernate task = new TaskHibernate();

    OperationsUtil.copyObjectProperties(task, newTask);

    criteriaGroup
        .select(rootGroup)
        .where(builder.like(rootGroup.get("groupName"), group.getGroupName()));

    Query<Group> queryGroup = session.createQuery(criteriaGroup);
    GroupHibernate selectedGroup;

    try {
      selectedGroup = (GroupHibernate) queryGroup.getSingleResult();
    } catch (Exception e) {
      logger.error(e);
      session.close();
      return null;
    }

    try {
      task.setGroup(selectedGroup);
      session.save(task);
    } catch (Exception e) {
      logger.error(e);
      session.close();
      return null;
    }

    session.close();
    return task;
  }
}
