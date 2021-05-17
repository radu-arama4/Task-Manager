package com.stefanini.taskmanager.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import com.stefanini.taskmanager.persistence.entity.User;

public class HibernateTest {

  @Test
  public void test() {
    StandardServiceRegistry ssr =
        new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

    SessionFactory factory = meta.getSessionFactoryBuilder().build();
    Session session = factory.openSession();
    Transaction t = session.beginTransaction();

    User e1 = new User();
    e1.setFirstName("Gaurav");
    e1.setLastName("Chawla");

    session.save(e1);
    t.commit();
    System.out.println("successfully saved");
    factory.close();
    session.close();
  }

}
