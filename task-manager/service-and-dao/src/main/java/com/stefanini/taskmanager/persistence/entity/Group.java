package com.stefanini.taskmanager.persistence.entity;

public interface Group {
    Long getGroupId();
    String getGroupName();
    void setGroupId(Long groupId);
    void setGroupName(String groupName);
}
