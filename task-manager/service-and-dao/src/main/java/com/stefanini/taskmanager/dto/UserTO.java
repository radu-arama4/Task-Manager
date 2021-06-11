package com.stefanini.taskmanager.dto;

import com.stefanini.taskmanager.service.proxy.email.EmailField;
import com.stefanini.taskmanager.service.proxy.email.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Email(emailMessage = "User {firstName} / {lastName} identified by {userName} has been created")
public class UserTO {
  private Long userId;
  @EmailField(fieldName = "firstName")
  private String firstName;
  @EmailField(fieldName = "lastName")
  private String lastName;
  @EmailField(fieldName = "userName")
  private String userName;

  public UserTO(String userName) {
    this.userName = userName;
  }

  public UserTO(String firstName, String lastName, String userName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }
}
