package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.UserTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserParserTest {

  UserParser userParser = new UserParser();

  //TODO fix static

  @Test
  public void testSuccessful() {
    String firstName = "Test1";
    String lastName = "Test2";
    String userName = "Test3";

    String[] arguments =
        new String[] {
          "-createUser",
          "-fn='" + firstName + "'",
          "-ln='" + lastName + "'",
          "-un='" + userName + "'"
        };

    UserTO user = userParser.parseUser(arguments);

    assertEquals(firstName, user.getFirstName());
    assertEquals(lastName, user.getLastName());
    assertEquals(userName, user.getUserName());
  }

  @Test
  public void testUserIncomplete() {
    String firstName = "Test1";
    String lastName = "Test2";

    String[] arguments =
        new String[] {"-createUser", "-fn='" + firstName + "'", "-ln='" + lastName + "'"};

    UserTO user = userParser.parseUser(arguments);

    assertEquals(firstName, user.getFirstName());
    assertEquals(lastName, user.getLastName());
    assertNull(user.getUserName());
  }
}
