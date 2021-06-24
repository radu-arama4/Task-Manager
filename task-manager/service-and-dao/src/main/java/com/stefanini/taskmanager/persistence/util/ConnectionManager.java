package com.stefanini.taskmanager.persistence.util;

import com.stefanini.taskmanager.persistence.dao.DaoType;
import com.stefanini.taskmanager.persistence.util.exceptions.UnsupportedDaoTypeException;
import com.stefanini.taskmanager.util.ApplicationProperties;

/**
 * Abstract class which provides methods for connecting and disconnecting to the database depending
 * on the configured dao type.
 */
public abstract class ConnectionManager {
  private static final DaoType daoType = ApplicationProperties.getInstance().getDaoType();

  /**
   * Connects to the database based on the configured dao type.
   *
   * @throws UnsupportedDaoTypeException
   */
  public static void connectToDataBase() throws UnsupportedDaoTypeException {
    switch (daoType) {
      case JDBC:
        {
          DataBaseUtil.connectWithJdbc();
          break;
        }
      case HIBERNATE:
        {
          DataBaseUtil.connectWithHibernate();
          break;
        }
      default:
        {
          throw new UnsupportedDaoTypeException();
        }
    }
  }

  /**
   * Disconnects from the database based on the configured dao type.
   *
   * @throws UnsupportedDaoTypeException
   */
  public static void disconnectFromDataBase() throws UnsupportedDaoTypeException {
    switch (daoType) {
      case JDBC:
        {
          DataBaseUtil.disconnectJDBC();
          break;
        }
      case HIBERNATE:
        {
          DataBaseUtil.disconnectHibernate();
          break;
        }
      default:
        {
          throw new UnsupportedDaoTypeException();
        }
    }
  }
}
