package com.stefanini.taskmanager.persistence.dao.factory;

import com.stefanini.taskmanager.persistence.dao.*;

/**
 * Implementation of {@link DaoFactory} for providing methods for producing single instances of
 * Hibernate Dao classes.
 */
public class HibernateDaoFactory implements DaoFactory {
  public UserDao createUserDao() {
    return null;
  }

  public TaskDao createTaskDao() {
    return null;
  }

  public GroupDao createGroupDao() {
    return null;
  }
}
