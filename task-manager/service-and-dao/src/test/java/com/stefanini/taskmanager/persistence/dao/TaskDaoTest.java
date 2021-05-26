package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.dao.factory.JdbcDaoFactory;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TaskDaoTest {
  DaoFactory daoFactory = new JdbcDaoFactory();
  TaskDao taskDao = daoFactory.createTaskDao();
  UserTO testUser = new UserTO(null, null, "jora");

  @Test
  public void testAddTask() {
    TaskTO testTask = new TaskTO("Test", "dudsadasd");
    TaskTO returnedTestTask = taskDao.addTask(testTask, testUser);
    assertNotNull(returnedTestTask.getId());
  }

  @Test
  public void testShowTasks() {
    TaskTO testTask = new TaskTO("Test", "TestTest2");
    taskDao.addTask(testTask, testUser);
    List<TaskTO> tasks = taskDao.getTasks(testUser);
    Optional<TaskTO> result = tasks.stream().filter(task -> task.equals(testTask)).findAny();
    assertTrue(result.isPresent());
  }
}
