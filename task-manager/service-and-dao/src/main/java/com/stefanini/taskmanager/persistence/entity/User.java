package com.stefanini.taskmanager.persistence.entity;

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
