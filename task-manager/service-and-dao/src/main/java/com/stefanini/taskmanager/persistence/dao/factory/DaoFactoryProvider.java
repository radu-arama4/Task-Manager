package com.stefanini.taskmanager.persistence.dao.factory;

import com.stefanini.taskmanager.persistence.util.exceptions.UnsupportedDaoTypeException;

/**
 * Abstract class containing static method for generating a {@link DaoFactory} depending on the
 * given input.
 */
public abstract class DaoFactoryProvider {
  public static DaoFactory createDaoFactory(DaoType daoType) {
    switch (daoType) {
      case JDBC:
        return new JdbcDaoFactory();
      case HIBERNATE:
        return new HibernateDaoFactory();
      default:
        throw new UnsupportedDaoTypeException();
    }
  }
}
