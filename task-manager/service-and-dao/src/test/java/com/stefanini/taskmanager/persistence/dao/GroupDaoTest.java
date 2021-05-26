package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.JdbcDaoFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class GroupDaoTest {
  DaoFactory daoFactory = new JdbcDaoFactory();
  GroupDao groupDao = daoFactory.createGroupDao();

  @Test
  public void testCreateGroup() {
    GroupTO testGroup = new GroupTO("TestGroupp");
    GroupTO resultGroup = groupDao.createGroup(testGroup);
    assertNotNull(resultGroup.getId());
  }

  @Test
  public void testAddUserToGroup() {}

  @Test
  public void testAddTaskToGroup() {
    fail("Not yet implemented");
  }
}
