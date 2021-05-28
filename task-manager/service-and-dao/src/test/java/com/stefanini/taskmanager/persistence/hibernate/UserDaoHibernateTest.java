package com.stefanini.taskmanager.persistence.hibernate;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.hibernate.UserDaoHibernate;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserDaoHibernateTest {
  Session session = null;
  SessionFactory factory = null;
  UserDaoHibernate userDaoHibernate;

  @Before
  public void init() {
    StandardServiceRegistry ssr =
        new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

    this.factory = meta.getSessionFactoryBuilder().build();
    this.session = factory.openSession();

    this.userDaoHibernate = new UserDaoHibernate(session);
  }

  @Test
  public void createUserTest() {
    UserTO user = new UserTO("bas", "sadas", "asdads");
    userDaoHibernate.createUser(user);
  }

  @Test
  public void getUsersTest() {
    userDaoHibernate.getUsers();
  }

  @Test
  public void makeTransaction() {
    User user = new User("jora", "jora", "Tesdst");
    Task task = new Task("do smth", "something very serious");

    Transaction transaction = null;

    try{
      transaction = session.beginTransaction();
      task.setUser(user);

      session.save(task);
      session.save(user);

      transaction.commit();
    }catch (Exception e){
      if (transaction != null) {
        transaction.rollback();
      }
    }
  }

  @After
  public void finish() {
    factory.close();
    session.close();
  }
}
