package com.stefanini.taskmanager.persistence.dao.factory;

import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.hibernate.GroupDaoHibernate;
import com.stefanini.taskmanager.persistence.dao.hibernate.TaskDaoHibernate;
import com.stefanini.taskmanager.persistence.dao.hibernate.UserDaoHibernate;
import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.hibernate.SessionFactory;

/**
 * Implementation of {@link DaoFactory} for providing methods for producing single instances of
 * Hibernate Dao classes.
 */
public class HibernateDaoFactory implements DaoFactory {
  SessionFactory sessionFactory = DataBaseUtil.connectWithHibernate();

  public UserDao createUserDao() {
    return UserDaoHibernate.getInstance(sessionFactory);
  }

  public TaskDao createTaskDao() {
    return TaskDaoHibernate.getInstance(sessionFactory);
  }

  public GroupDao createGroupDao() {
    return GroupDaoHibernate.getInstance(sessionFactory);
  }
}
