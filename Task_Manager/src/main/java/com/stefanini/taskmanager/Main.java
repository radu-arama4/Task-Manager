package com.stefanini.taskmanager;

import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.GroupServiceImpl;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;

public class Main {

	public static void main(String[] args) {
		UserService userService = new UserServiceImpl();
		TaskService taskService = new TaskServiceImpl();
		GroupService groupService = new GroupServiceImpl();
		
		if(args.length==0){
            return;
        }

        //Checking all the accepted variants of arguments
        //Calling the methods from Controller
        switch (args[0]){
            case "-createUser":
            	userService.createUser(args);
                break;
            case "-showAllUsers":
                userService.showAllUsers();
                break;
            case "-addTask":
            	taskService.addTask(args);
                break;
            case "-showTasks":
            	taskService.showTasks(args);
                break;
            case "-createGroup":
            	groupService.createGroup(args);
                break;
            case "-addUserToGroup":
            	groupService.addUserToGroup(args);
                break;
            case "-addTaskToGroup":
            	groupService.addTaskToGroup(args);
                break;
            default:
                System.out.println("Incorrect command! Try again!");
                break;
        }
	}

}