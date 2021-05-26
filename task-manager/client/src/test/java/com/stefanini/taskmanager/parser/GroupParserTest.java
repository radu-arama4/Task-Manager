package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.GroupTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroupParserTest {

  GroupParser groupParser = new GroupParser();

  @Test
  public void testSuccessful() {
    String groupName = "Test";
    String[] arguments = new String[] {"-gn='" + groupName + "'"};
    GroupTO group = groupParser.parseGroup(arguments);

    assertEquals(groupName, group.getGroupName());
  }

  @Test
  public void testTaskIncomplete() {
    String[] arguments = new String[] {""};
    GroupTO group = groupParser.parseGroup(arguments);

    assertNull(group.getGroupName());
  }
}
