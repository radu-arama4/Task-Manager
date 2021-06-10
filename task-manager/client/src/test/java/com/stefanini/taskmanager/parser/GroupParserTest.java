package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.GroupTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroupParserTest {
  @Test
  public void testSuccessful() {
    String groupName = "Test";
    String[] arguments = new String[] {"-gn='" + groupName + "'"};
    GroupTO group = GroupParser.parseGroup(arguments);

    assertEquals(groupName, group.getGroupName());
  }

  @Test
  public void testTaskIncomplete() {
    String[] arguments = new String[] {""};
    GroupTO group = GroupParser.parseGroup(arguments);

    assertNull(group.getGroupName());
  }
}
