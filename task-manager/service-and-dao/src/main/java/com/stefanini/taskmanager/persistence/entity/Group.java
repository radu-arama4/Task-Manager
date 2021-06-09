package com.stefanini.taskmanager.persistence.entity;

/**
 * Interface that represents the Group entity. Contains the getters and setters of the contained
 * fields.
 */
public interface Group {
  Long getGroupId();

  String getGroupName();

  void setGroupId(Long groupId);

  void setGroupName(String groupName);
}
