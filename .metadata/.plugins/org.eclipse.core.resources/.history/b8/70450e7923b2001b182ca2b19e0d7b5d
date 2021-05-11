package com.stefanini.taskmanager.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import com.stefanini.taskmanager.dto.User;

/**
 * 
 * Interface UserDao provides methods
 * for performing some crud operations
 * on User entities.
 * 
 * @author rarama
 *
 */
public interface UserDao {
	
	/**
	 * 
	 * Method used for adding a new
	 * User entity to the database
	 * 
	 * @param newUser
	 * @return boolean value
	 */
	boolean createUser(User newUser);
	
	/**
	 * 
	 * Method used for extracting all
	 * existing User entities from
	 * the database in form of list
	 * 
	 * @return List<User>
	 * @throws SQLException
	 */
	List<User> getUsers() throws SQLException;
	
}
