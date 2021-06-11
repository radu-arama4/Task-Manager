package com.stefanini.taskmanager.persistence.util.context;

import com.stefanini.taskmanager.persistence.dao.factory.DaoType;
import com.stefanini.taskmanager.persistence.util.exceptions.UnsupportedDaoTypeException;
import com.stefanini.taskmanager.util.ApplicationProperties;

/** Returns a transaction context based on the configured dao type in the configuration file. */
public class ContextProvider {
  private static final DaoType daoType = ApplicationProperties.getInstance().getDaoType();

  public static TransactionContext createTransactionContext() throws UnsupportedDaoTypeException {
    switch (daoType) {
      case JDBC:
        return new JdbcTransactionContext();
      case HIBERNATE:
        return new HibernateTransactionContext();
      default:
        throw new UnsupportedDaoTypeException();
    }
  }
}
