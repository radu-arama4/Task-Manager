package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.JdbcDaoFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDaoTest {
  DaoFactory daoFactory = new JdbcDaoFactory();
  UserDao userDao = daoFactory.createUserDao();

  @Test(expected = SQLException.class)
  public void testCreateUser() {
//    UserTO testUser = new UserTO("Test", "Test", "dudsadasd");
//    UserTO returnedTestUser = userDao.createUser(testUser);
//
//    assertNotNull(returnedTestUser.getUserId());
//
//    returnedTestUser = userDao.createUser(testUser);
//    assertNull(returnedTestUser);
  }

  @Test
  public void testGetUsers() {
//    UserTO testUser = new UserTO("Test", "Test", "dummyasdd");
//    List<UserTO> users = userDao.getUsers();
//    Optional<UserTO> result = users.stream().filter(a -> a.equals(testUser)).findAny();
//
//    assertFalse(result.isPresent());
//
//    userDao.createUser(testUser);
//    users = userDao.getUsers();
//    result = users.stream().filter(a -> a.equals(testUser)).findAny();
//
//    assertTrue(result.isPresent());
  }
}
