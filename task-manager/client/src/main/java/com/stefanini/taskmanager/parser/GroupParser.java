package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.Group;

/**
 * Contains the necessary method/methods for parsing {@link Group} related input.
 */
public class GroupParser {
  private static final String GN = CommandLineVariables.GROUP_NAME.value;

  /**
   *
   * Extracts and encapsulates the {@link Group} related data from the command line arguments
   *
   * @param arguments - command line arguments
   * @return object of type {@link Group}
   */
  public static Group parseGroup(String[] arguments) {
    String groupName = null;

    for (String arg : arguments) {
      if (arg.startsWith(GN) && arg.endsWith("'")) {
        groupName = arg.substring(GN.length(), arg.length() - 1);
      }
    }

    return new Group(groupName);
  }
}
