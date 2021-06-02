package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.GroupTO;

/** Contains the necessary method/methods for parsing {@link GroupTO} related input. */
public class GroupParser {
  private static final String GN = CommandLineVariables.GROUP_NAME.value;

  /**
   * Extracts and encapsulates the {@link GroupTO} related data from the command line arguments
   *
   * @param arguments - command line arguments
   * @return object of type {@link GroupTO}
   */
  public static GroupTO parseGroup(String[] arguments) {
    String groupName = null;

    for (String arg : arguments) {
      if (arg.startsWith(GN) && arg.endsWith("'")) {
        groupName = arg.substring(GN.length(), arg.length() - 1);
      }
    }

    return new GroupTO(groupName);
  }
}
