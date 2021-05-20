package com.stefanini.taskmanager.persistence.dao.factory;

public abstract class DaoFactoryProduction {
    static public DaoFactory createDaoFactory(String daoCategory){
        switch (daoCategory){
            case "jdbc": return new JdbcDaoFactory();
            case "hibernate": return new HibernateDaoFactory();
            default: return null;
        }
    }
}
