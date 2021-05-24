package com.stefanini.taskmanager.persistence.dao.factory;

/**
 * Abstract class containing static method for generating a {@link DaoFactory} depending on the given
 * input.
 */
public abstract class DaoFactoryProvider {
  public static DaoFactory createDaoFactory(DaoType daoType) throws Exception {
    switch (daoType) {
      case JDBC:
        return new JdbcDaoFactory();
      case HIBERNATE:
        return new HibernateDaoFactory();
      default:
        throw new Exception("Undefined DAO type!");
    }
  }
}
