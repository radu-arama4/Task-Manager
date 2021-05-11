package com.stefanini.taskmanager.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;

public interface TaskDao {
	
	boolean addTask(Task task, User user);
	
	List<Task> showTasks(User selectedUser) throws SQLException;
	
}
