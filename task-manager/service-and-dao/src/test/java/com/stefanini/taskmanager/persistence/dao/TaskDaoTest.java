package com.stefanini.taskmanager.persistence.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;

public class TaskDaoTest {

  TaskDao taskDao = TaskDaoImpl.getInstance();

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
