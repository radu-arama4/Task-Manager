package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.entity.Group;
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

@Component
@Qualifier("hibernate")
@Scope("singleton")
public class GroupDaoHibernate implements GroupDao {
  private final SessionFactory sessionFactory;
  private static final Logger logger = LogManager.getLogger(GroupDaoHibernate.class);
  private final CriteriaQuery<User> criteriaUser;
  private final CriteriaQuery<Task> criteriaTask;
  private final CriteriaQuery<Group> criteriaGroup;
  private final CriteriaBuilder builder;
  private final Root<Task> rootTask;
  private final Root<Group> rootGroup;
  private final Root<User> rootUser;

  public GroupDaoHibernate(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    builder = sessionFactory.getCriteriaBuilder();
    criteriaUser = builder.createQuery(User.class);
    criteriaTask = builder.createQuery(Task.class);
    criteriaGroup = builder.createQuery(Group.class);
    rootUser = criteriaUser.from(User.class);
    rootTask = criteriaTask.from(Task.class);
    rootGroup = criteriaGroup.from(Group.class);
  }

  @Override
  public Group createGroup(Group group) {
    Session session;

    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    try {
      session.save(group);
    } catch (Exception e) {
      logger.error(e);
      return null;
    }
    return group;
  }

  @Override
  public User addUserToGroup(User user, Group group) {
    Session session;

    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    User selectedUser;
    Group selectedGroup;

    criteriaUser.select(rootUser).where(builder.like(rootUser.get("userName"), user.getUserName()));
    criteriaGroup
        .select(rootGroup)
        .where(builder.like(rootGroup.get("groupName"), group.getGroupName()));

    Query<User> queryUser = session.createQuery(criteriaUser);
    Query<Group> queryGroup = session.createQuery(criteriaGroup);

    try {
      selectedUser = (User) queryUser.getSingleResult();
      selectedGroup = (Group) queryGroup.getSingleResult();
    } catch (NoResultException e) {
      logger.error(e);
      return null;
    }

    try {
      selectedGroup.addUser(selectedUser);
    } catch (Exception e) {
      logger.error(e);
      return null;
    }

    return selectedUser;
  }

  @Override
  public Task addTaskToGroup(Task newTask, Group group) {
    Session session;

    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    Task task = new Task();

    OperationsUtil.copyObjectProperties(task, newTask);

    criteriaGroup
        .select(rootGroup)
        .where(builder.like(rootGroup.get("groupName"), group.getGroupName()));

    Query<Group> queryGroup = session.createQuery(criteriaGroup);
    Group selectedGroup;

    try {
      selectedGroup = (Group) queryGroup.getSingleResult();
    } catch (Exception e) {
      logger.error(e);
      return null;
    }

    try {
      task.setGroup(selectedGroup);
      session.save(task);
    } catch (Exception e) {
      logger.error(e);
      return null;
    }

    return task;
  }
}
