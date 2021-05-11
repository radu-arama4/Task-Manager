package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.Operation;
import com.stefanini.taskmanager.receivers.UserReceiver;

public class ShowAllUsersOperation implements Operation{
	
	UserReceiver user;
	
	public ShowAllUsersOperation(UserReceiver user) {
		this.user = user;
	}

	@Override
	public void execute() {
		user.showAllUsers();
	}
}
