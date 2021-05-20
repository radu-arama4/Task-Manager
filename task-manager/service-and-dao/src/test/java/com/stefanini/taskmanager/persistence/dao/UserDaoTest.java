package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.JdbcDaoFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserDaoTest {

  DaoFactory daoFactory = new JdbcDaoFactory();
  UserDao userDao = daoFactory.createUserDao();

  @Test(expected = SQLException.class)
  public void testCreateUser() {

    User testUser = new User("Test", "Test", "dudsadasd");
    User returnedTestUser = userDao.createUser(testUser);

    assertNotNull(returnedTestUser.getId());

    returnedTestUser = userDao.createUser(testUser);

    assertEquals(returnedTestUser, null);
  }

  // TODO comment

  @Test
  public void testGetUsers() {

    User testUser = new User("Test", "Test", "dummyasdd");

    List<User> users = userDao.getUsers();

    Optional<User> result = users.stream().filter(a -> a.equals(testUser)).findAny();

    assertFalse(result.isPresent());

    userDao.createUser(testUser);

    users = userDao.getUsers();

    result = users.stream().filter(a -> a.equals(testUser)).findAny();

    assertTrue(result.isPresent());
  }
}
