package com.stefanini.taskmanager.persistence;

import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

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

    User e2 = new User("asda", "das", "sad");

    Task t1 = new Task();
    t1.setTaskTitle("Super title");
    t1.setTaskDescription("This is my task for today");

    Task t2 = new Task();
    t2.setTaskTitle("Super title 2 ");
    t2.setTaskDescription("This is my task for today2 ");

    e1.addTask(t1);

    Group g1 = new Group("MyGroup");
    Group g2 = new Group("MyGroup");
    g1.addTask(t2);
    g1.addUser(e2);
    g2.addUser(e2);

    session.save(t1);
    session.save(t2);
    session.save(e1);
    session.save(e2);
    session.save(g1);
    session.save(g2);
    t.commit();
    System.out.println("successfully saved");
    factory.close();
    session.close();
  }

}
