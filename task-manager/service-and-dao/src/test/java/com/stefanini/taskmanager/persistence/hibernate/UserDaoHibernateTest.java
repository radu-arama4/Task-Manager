package com.stefanini.taskmanager.persistence.hibernate;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.hibernate.UserDaoHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.*;

public class UserDaoHibernateTest {
    Session session = null;
    SessionFactory factory = null;
    UserDaoHibernate userDaoHibernate;

    @Before
    public void init(){
        StandardServiceRegistry ssr =
                new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        this.factory = meta.getSessionFactoryBuilder().build();
        this.session = factory.openSession();

        this.userDaoHibernate = new UserDaoHibernate(session);
    }

    @Test
    public void createUserTest(){
        UserTO user = new UserTO("bas", "sadas", "asdads");
        userDaoHibernate.createUser(user);
    }

    @Test
    public void getUsersTest(){
        userDaoHibernate.getUsers();
    }

    @After
    public void finish(){
        factory.close();
        session.close();
    }
}
