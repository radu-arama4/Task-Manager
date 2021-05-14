package com.stefanini.taskmanager.persistence.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import com.stefanini.taskmanager.dto.Group;

public class GroupDaoTest {

  GroupDao groupDao = GroupDaoImpl.getInstance();

  @Test
  public void testCreateGroup() {

    Group testGroup = new Group("TestGroup");

    Group resultGroup = groupDao.createGroup(testGroup);

    assertNotNull(resultGroup.getId());

  }

  @Test
  public void testAddUserToGroup() {



  }

  @Test
  public void testAddTaskToGroup() {
    fail("Not yet implemented");
  }

}
