package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.UserTO;

/** Contains the necessary method/methods for parsing {@link UserTO} related input. */
public class UserParser {
  private static final String FN = CommandLineVariables.FIRST_NAME.value;
  private static final String LN = CommandLineVariables.LAST_NAME.value;
  private static final String UN = CommandLineVariables.USER_NAME.value;

  /**
   * Extracts and encapsulates the {@link UserTO} related data from the command line arguments
   *
   * @param arguments - command line arguments
   * @return object of type {@link UserTO}
   */
  public static UserTO parseUser(String[] arguments) {
    String firstName = null;
    String lastName = null;
    String userName = null;

    for (String arg : arguments) {
      if (arg.startsWith(FN) && arg.endsWith("'")) {
        firstName = arg.substring(FN.length(), arg.length() - 1);
      } else if (arg.startsWith(LN) && arg.endsWith("'")) {
        lastName = arg.substring(LN.length(), arg.length() - 1);
      } else if (arg.startsWith(UN) && arg.endsWith("'")) {
        userName = arg.substring(UN.length(), arg.length() - 1);
      }
    }

    return new UserTO(firstName, lastName, userName);
  }
}
