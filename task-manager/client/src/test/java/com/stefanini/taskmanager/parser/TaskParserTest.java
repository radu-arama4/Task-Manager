package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.Task;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskParserTest {

  TaskParser taskParser = new TaskParser();

  @Test
  public void testSuccessful() {
    String taskTitle = "Test1";
    String taskDescription = "Test2";
    String[] arguments = new String[] {"-tt='" + taskTitle + "'", "-td='" + taskDescription + "'"};

    Task task = taskParser.parseTask(arguments);

    assertEquals(taskTitle, task.getTaskTitle());
    assertEquals(taskDescription, task.getDescription());
  }

  @Test
  public void testIncompleteTask() {
    String taskDescription = "Test2";
    String[] arguments = new String[] {"-td='" + taskDescription + "'"};

    Task task = taskParser.parseTask(arguments);

    assertNull(task.getTaskTitle());
    assertEquals(taskDescription, task.getDescription());
  }
}
