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
import org.hibernate.Transaction;

import java.lang.reflect.InvocationTargetException;

public class GroupDaoHibernate implements GroupDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);

  private static final String GET_USER_BY_USERNAME = "from User where username =: username";
  private static final String GET_GROUP_BY_GROUP_NAME = "from group_ where group_name =: groupName";

  public GroupDaoHibernate(Session session) {
    this.session = session;
  }

  @Override
  public Group createGroup(Group group) {
    Transaction transaction = session.beginTransaction();

    try {
      session.save(group);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    return group;
  }

  @Override
  public User addUserToGroup(User user, Group group) {
    Transaction transaction = session.beginTransaction();

    UserHibernate selectedUser = null;
    GroupHibernate selectedGroup = null;

    try {
      selectedUser =
          (UserHibernate)
              session
                  .createQuery(GET_USER_BY_USERNAME)
                  .setParameter("username", user.getUserName())
                  .getSingleResult();

      selectedGroup =
          (GroupHibernate)
              session
                  .createQuery(GET_GROUP_BY_GROUP_NAME)
                  .setParameter("groupName", group.getGroupName())
                  .getSingleResult();
    } catch (NullPointerException e) {
      logger.error(e);
      return null;
    }

    try {
      selectedGroup.addUser(selectedUser);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    return user;
  }

  @Override
  public Task addTaskToGroup(Task newTask, Group newGroup) {
    Transaction transaction = session.beginTransaction();

    TaskHibernate task = new TaskHibernate();
    try {
      BeanUtils.copyProperties(task, newTask);
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }

    GroupHibernate selectedGroup =
        (GroupHibernate)
            session
                .createQuery(GET_GROUP_BY_GROUP_NAME)
                .setParameter("groupName", newGroup.getGroupName())
                .list()
                .get(0);

    try {
      task.setGroup(selectedGroup);
      session.save(task);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    return task;
  }
}
