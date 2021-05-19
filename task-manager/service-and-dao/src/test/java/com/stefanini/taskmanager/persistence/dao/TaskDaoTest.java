package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
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

  User testUser = new User(null, null, "jora");

  @Test
  public void testAddTask() {

    Task testTask = new Task("Test", "dudsadasd");

    Task returnedTestTask = taskDao.addTask(testTask, testUser);

    assertNotNull(returnedTestTask.getId());
  }

  @Test
  public void testShowTasks() {

    Task testTask = new Task("Test", "TestTest2");

    taskDao.addTask(testTask, testUser);

    List<Task> tasks = taskDao.showTasks(testUser);

    Optional<Task> result = tasks.stream().filter(task -> task.equals(testTask)).findAny();

    assertTrue(result.isPresent());
  }
}
