package com.stefanini.taskmanager.persistence.entity;

/**
 * Interface that represents the User entity. Contains the getters and setters of the contained
 * fields.
 */
public interface User {
  Long getUserId();

  String getUserName();

  String getFirstName();

  String getLastName();

  void setUserId(Long userId);

  void setUserName(String userName);

  void setFirstName(String firstName);

  void setLastName(String lastName);
}
