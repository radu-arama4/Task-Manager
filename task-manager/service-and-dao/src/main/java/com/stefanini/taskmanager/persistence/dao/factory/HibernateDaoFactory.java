package com.stefanini.taskmanager.persistence.dao.factory;

import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.hibernate.GroupDaoHibernate;
import com.stefanini.taskmanager.persistence.dao.hibernate.TaskDaoHibernate;
import com.stefanini.taskmanager.persistence.dao.hibernate.UserDaoHibernate;
import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.hibernate.Session;

/**
 * Implementation of {@link DaoFactory} for providing methods for producing single instances of
 * Hibernate Dao classes.
 */
public class HibernateDaoFactory implements DaoFactory {
  Session session = DataBaseUtil.connectToHibernate();

  public UserDao createUserDao() {
    return new UserDaoHibernate(session);
  }

  public TaskDao createTaskDao() {
    return new TaskDaoHibernate(session);
  }

  public GroupDao createGroupDao() {
    return new GroupDaoHibernate(session);
  }
}
