package com.stefanini.taskmanager.service;

import java.sql.SQLException;
import java.util.List;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.daoImpl.TaskDaoImpl;
import com.stefanini.taskmanager.persistence.daoImpl.UserDaoImpl;

public class TaskServiceImpl implements TaskService{

	TaskDao taskDao = TaskDaoImpl.getInstance();
	UserDao userDao = UserDaoImpl.getInstance();
	
	@Override
	public boolean addTask(String[] arguments) {
		String userName = null;
        String taskTitle = null;
        String taskDescription = null;

        StringBuilder taskT = new StringBuilder();
        StringBuilder taskD = new StringBuilder();
        String prev = "";

        int count = 0;

        for(String arg:arguments){
            if(arg.startsWith("-un='") && arg.endsWith("'")){
                userName = arg.substring(5, arg.length()-1);
                count++;
            }
            else if(arg.startsWith("-tt='") && arg.endsWith("'")){
                taskTitle = arg.substring(5, arg.length()-1);
                count++;
            }
            else if(arg.startsWith("-tt='")){
                prev = "-tt='";
                taskT.append(arg, 5, arg.length());
                taskT.append(" ");
                count++;
            }
            else if(prev.equals("-tt='")){
                if(arg.endsWith("'")){
                    taskT.append(arg, 0, arg.length()-1);
                    taskTitle = taskT.toString();
                    prev = "";
                }else {
                    taskT.append(arg).append(" ");
                }
            }
            else if(arg.startsWith("-td='") && arg.endsWith("'")){
                taskDescription = arg.substring(5, arg.length()-1);
                count++;
            }
            else if(arg.startsWith("-td='")){
                prev = "-td='";
                taskD.append(arg, 5, arg.length());
                taskD.append(" ");
                count++;
            }
            else if(prev.equals("-td='")){
                if(arg.endsWith("'")){
                    taskD.append(arg, 0, arg.length()-1);
                    taskDescription = taskD.toString();
                    prev = "";
                }else {
                    taskD.append(arg).append(" ");
                }
            }
        }

        if(count>3){
            System.out.println("Too many arguments!");
            return false;
        }
        
        if(userName==null || taskTitle==null || taskDescription==null){
            System.out.println("Information missing!");
        }else{
                   if(taskDao.addTask(new Task(taskTitle, taskDescription), 
                		   new User(null, null, userName))){
                        System.out.println("Task added to " + userName);
                        return true;
                   }
            System.out.println("No such user!");
        }
        return false;
	}

	@Override
	public List<Task> showTasks(String[] arguments) {
		
		if(arguments.length>2){
            System.out.println("Too many arguments!");
            return null;
        }

        String userName = null;
        String arg = arguments[1];

        if(arg.startsWith("-un='") && arg.endsWith("'")){
            userName = arg.substring(5, arg.length()-1);
        }

        try {
			List<Task> tasks = taskDao.showTasks(new User(null, null, userName));
			return tasks;
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        System.out.println("No such user!");
		
		return null;
	}

}
