package com.stefanini.taskmanager.persistence.entity;

import com.stefanini.taskmanager.persistence.dao.factory.DaoType;
import com.stefanini.taskmanager.persistence.entity.hibernate.GroupHibernate;
import com.stefanini.taskmanager.persistence.entity.hibernate.TaskHibernate;
import com.stefanini.taskmanager.persistence.entity.hibernate.UserHibernate;
import com.stefanini.taskmanager.persistence.entity.jdbc.GroupJdbc;
import com.stefanini.taskmanager.persistence.entity.jdbc.TaskJdbc;
import com.stefanini.taskmanager.persistence.entity.jdbc.UserJdbc;
import com.stefanini.taskmanager.util.ApplicationProperties;

public class EntityFactory {
  private static final ApplicationProperties applicationProperties =
      ApplicationProperties.getInstance();
  private static final DaoType daoType = applicationProperties.getDaoType();

  public static User createUser() {
    switch (daoType) {
      case HIBERNATE:
        {
          return new UserHibernate();
        }
      case JDBC:
        {
          return new UserJdbc();
        }
      default:
        {
          return null;
        }
    }
  }

  public static Task createTask() {
    switch (daoType) {
      case HIBERNATE:
        {
          return new TaskHibernate();
        }
      case JDBC:
        {
          return new TaskJdbc();
        }
      default:
        {
          return null;
        }
    }
  }

  public static Group createGroup() {
    switch (daoType) {
      case HIBERNATE:
        {
          return new GroupHibernate();
        }
      case JDBC:
        {
          return new GroupJdbc();
        }
      default:
        {
          return null;
        }
    }
  }
}
