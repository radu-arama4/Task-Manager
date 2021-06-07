package com.stefanini.taskmanager.persistence.entity;

public interface User {
    String getUserName();
    String getFirstName();
    String getLastName();
    void setUserName(String userName);
    void setFirstName(String firstName);
    void setLastName(String lastName);
}
