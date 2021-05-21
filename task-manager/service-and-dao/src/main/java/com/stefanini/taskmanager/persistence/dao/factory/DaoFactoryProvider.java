package com.stefanini.taskmanager.persistence.dao.factory;

public abstract class DaoFactoryProvider {
    static public DaoFactory createDaoFactory(DaoType daoType){
        switch (daoType){
            case JDBC: return new JdbcDaoFactory();
            case HIBERNATE: return new HibernateDaoFactory();
            default: return null;
        }
    }
}
