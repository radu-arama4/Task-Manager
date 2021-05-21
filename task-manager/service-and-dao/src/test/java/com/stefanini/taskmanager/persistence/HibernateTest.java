package com.stefanini.taskmanager.persistence;

import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.persistence.entity.Task;
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
    e1.setUserName("Jora");

    Task t1 = new Task();
    t1.setTaskTitle("Super title");
    t1.setTaskDescription("This is my task for today");

    Task t2 = new Task();
    t1.setTaskTitle("Super title 2 ");
    t1.setTaskDescription("This is my task for today2 ");

    Group g1 = new Group();
    g1.setGroupName("My Super group");

    g1.getTasks().add(t2);
    e1.getTasks().add(t1);

    g1.getUsers().add(e1);

    session.save(e1);
    session.save(t1);
    session.save(t2);
    session.save(g1);
    t.commit();
    System.out.println("successfully saved");
    factory.close();
    session.close();
  }

}
