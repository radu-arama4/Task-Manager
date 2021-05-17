package com.stefanini.taskmanager.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import com.stefanini.taskmanager.dto.User;

public class UserDaoTest {

  UserDao userDao = UserDaoImpl.getInstance();

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
