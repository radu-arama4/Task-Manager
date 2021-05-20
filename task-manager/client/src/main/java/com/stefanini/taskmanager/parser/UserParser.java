package com.stefanini.taskmanager.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.User;

/**
 * Contains the necessary method/methods for parsing {@link User} related input.
 */
public class UserParser {
  private static final Logger logger = LogManager.getLogger(UserParser.class);

  private static final String FN = CommandLineVariables.FIRST_NAME.value;
  private static final String LN = CommandLineVariables.LAST_NAME.value;
  private static final String UN = CommandLineVariables.USER_NAME.value;

  /**
   *
   * Extracts and encapsulates the {@link User} related data from the command line arguments
   *
   * @param arguments - command line arguments
   * @return object of type {@link User}
   */
  public static User parseUser(String[] arguments) {
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

    return new User(firstName, lastName, userName);
  }
}
