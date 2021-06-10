package com.stefanini.taskmanager.persistence.util.context;

import com.stefanini.taskmanager.persistence.dao.factory.DaoType;
import com.stefanini.taskmanager.util.ApplicationProperties;

public class ContextProvider {
  private static final DaoType daoType = ApplicationProperties.getInstance().getDaoType();

  public static TransactionContext createTransactionContext() {
    switch (daoType) {
      case JDBC:
        return new JdbcTransactionContext();
      case HIBERNATE:
        return new HibernateTransactionContext();
      default:
        return null; //exception
    }
  }
}
