package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.TaskTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains the necessary method/methods for parsing {@link TaskTO} related input.
 */
public class TaskParser {
  private static final Logger logger = LogManager.getLogger(TaskParser.class);

  private static final String TT = CommandLineVariables.TASK_TITLE.value;
  private static final String TD = CommandLineVariables.TASK_DESCRIPTION.value;

  /**
   *
   * Extracts and encapsulates the {@link TaskTO} related data from the command line arguments
   *
   * @param arguments - command line arguments
   * @return object of type {@link TaskTO}
   */
  public static TaskTO parseTask(String[] arguments) {
    String taskTitle = null;
    String taskDescription = null;

    StringBuilder taskT = new StringBuilder();
    StringBuilder taskD = new StringBuilder();
    String prev = "";

    for (String arg : arguments) {
      if (arg.startsWith(TT) && arg.endsWith("'")) {
        taskTitle = arg.substring(TT.length(), arg.length() - 1);
      } else if (arg.startsWith(TT)) {
        prev = TT;
        taskT.append(arg, TT.length(), arg.length());
        taskT.append(" ");
      } else if (prev.equals(TT)) {
        if (arg.endsWith("'")) {
          taskT.append(arg, 0, arg.length() - 1);
          taskTitle = taskT.toString();
          prev = "";
        } else {
          taskT.append(arg).append(" ");
        }
      } else if (arg.startsWith(TD) && arg.endsWith("'")) {
        taskDescription = arg.substring(TD.length(), arg.length() - 1);
      } else if (arg.startsWith(TD)) {
        prev = TD;
        taskD.append(arg, TD.length(), arg.length());
        taskD.append(" ");
      } else if (prev.equals(TD)) {
        if (arg.endsWith("'")) {
          taskD.append(arg, 0, arg.length() - 1);
          taskDescription = taskD.toString();
          prev = "";
        } else {
          taskD.append(arg).append(" ");
        }
      }
    }

    return new TaskTO(taskTitle, taskDescription);
  }
}
