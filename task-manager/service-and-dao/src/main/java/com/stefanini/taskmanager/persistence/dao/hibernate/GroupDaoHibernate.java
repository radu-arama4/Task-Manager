package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.entity.hibernate.GroupHibernate;
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

public class GroupDaoHibernate implements GroupDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);
  private static GroupDao singleInstance;
  private final CriteriaQuery<User> criteriaUser;
  private final CriteriaQuery<Task> criteriaTask;
  private final CriteriaQuery<Group> criteriaGroup;
  private final CriteriaBuilder builder;
  private final Root<TaskHibernate> rootTask;
  private final Root<GroupHibernate> rootGroup;
  private final Root<UserHibernate> rootUser;

  private GroupDaoHibernate(Session session) {
    this.session = session;
    builder = session.getCriteriaBuilder();
    criteriaUser = builder.createQuery(User.class);
    criteriaTask = builder.createQuery(Task.class);
    criteriaGroup = builder.createQuery(Group.class);
    rootUser = criteriaUser.from(UserHibernate.class);
    rootTask = criteriaTask.from(TaskHibernate.class);
    rootGroup = criteriaGroup.from(GroupHibernate.class);
  }

  public static GroupDao getInstance(Session session) {
    if (singleInstance == null) {
      singleInstance = new GroupDaoHibernate(session);
    }
    return singleInstance;
  }

  @Override
  public Group createGroup(Group group) {
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
    TaskHibernate task = new TaskHibernate();

    try {
      BeanUtils.copyProperties(task, newTask);
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }

    criteriaGroup
        .select(rootGroup)
        .where(builder.like(rootGroup.get("groupName"), group.getGroupName()));

    Query<Group> queryGroup = session.createQuery(criteriaGroup);
    GroupHibernate selectedGroup;

    try {
      selectedGroup = (GroupHibernate) queryGroup.getSingleResult();
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
