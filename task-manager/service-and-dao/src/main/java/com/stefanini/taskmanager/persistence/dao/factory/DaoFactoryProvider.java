package com.stefanini.taskmanager.persistence.dao.factory;

/**
 * Abstract class containing static method for generating a {@link DaoFactory} depending on the
 * given input.
 */
public abstract class DaoFactoryProvider {
  public static DaoFactory createDaoFactory(DaoType daoType){
    switch (daoType) {
      case JDBC:
        return new JdbcDaoFactory();
      case HIBERNATE:
        return new HibernateDaoFactory();
      default:
        throw new DaoTypeException("Undefined DAO type!");
    }
  }

  static class DaoTypeException extends RuntimeException {
    public DaoTypeException(String s) {
      super(s);
    }
  }
}
