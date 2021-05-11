package com.stefanini.taskmanager.receivers;

import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;

public class UserReceiver {

	private String[] args = null;
	private UserService userService = null;
	
	public UserReceiver(String[] args) {
		this.args = args;
		userService = new UserServiceImpl();
	}

	public void addUser() {
		userService.createUser(args);
	}
	
	public void showAllUsers() {
		userService.showAllUsers();
	}
	
}
