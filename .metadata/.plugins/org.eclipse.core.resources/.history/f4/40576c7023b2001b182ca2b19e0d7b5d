package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;

/**
 * Interface GroupDao provides methods used
 * for group operations. 
 * 
 * @author rarama
 *
 */
public interface GroupDao {

	/**
	 * Method for adding a new Group entity 
	 * in the database
	 * @param group
	 * @return boolean value
	 */
	boolean createGroup(Group group);
	
	/**
	 * Method for adding a specific user
	 * to an existing group
	 * @param user
	 * @param group
	 * @return boolean value
	 */
	boolean addUserToGroup(User user, Group group);
	
	/**
	 * Method for adding a specific task
	 * to all the members of a group
	 * @param task
	 * @param group
	 * @return boolean value
	 */
	boolean addTaskToGroup(Task task, Group group);
	
}
