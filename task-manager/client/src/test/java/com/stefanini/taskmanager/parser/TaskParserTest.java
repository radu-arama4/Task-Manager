package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.TaskTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskParserTest {
  @Test
  public void testSuccessful() {
    String taskTitle = "Test1";
    String taskDescription = "Test2";
    String[] arguments = new String[] {"-tt='" + taskTitle + "'", "-td='" + taskDescription + "'"};

    TaskTO task = TaskParser.parseTask(arguments);

    assertEquals(taskTitle, task.getTaskTitle());
    assertEquals(taskDescription, task.getDescription());
  }

  @Test
  public void testIncompleteTask() {
    String taskDescription = "Test2";
    String[] arguments = new String[] {"-td='" + taskDescription + "'"};

    TaskTO task = TaskParser.parseTask(arguments);

    assertNull(task.getTaskTitle());
    assertEquals(taskDescription, task.getDescription());
  }
}
