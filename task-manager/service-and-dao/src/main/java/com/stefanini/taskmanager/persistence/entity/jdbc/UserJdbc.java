package com.stefanini.taskmanager.persistence.entity.jdbc;

import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.proxy.email.Email;
import com.stefanini.taskmanager.service.proxy.email.EmailField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Email(emailMessage = "User {firstName} / {lastName} identified by {userName} has been created")
public class UserJdbc implements User {
  private Long userId;

  @EmailField(fieldName = "firstName")
  private String firstName;

  @EmailField(fieldName = "lastName")
  private String lastName;

  @EmailField(fieldName = "userName")
  private String userName;
}
