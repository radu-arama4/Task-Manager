package com.stefanini.taskmanager.persistence.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.util.DButil;

public class TaskDaoImpl implements TaskDao{

	private static TaskDaoImpl singleInstance = null;
	
	private Connection connection = null;
	
	private TaskDaoImpl() {
		connection = DButil.connectToDb();
	}
	
	static public TaskDaoImpl getInstance() {
		if(singleInstance == null) {
			singleInstance = new TaskDaoImpl();
		}
		return singleInstance;
	}
	
	@Override
	public boolean addTask(Task task, User user) {
		try {
            String userName = user.getUserName();
            String taskTitle = task.getTaskTitle();
            String taskDescription = task.getDescription();

            String query = "INSERT INTO sys.task(title, description, user_Id) \r\n"
            		+ "SELECT '"+ taskTitle +"', '"+ taskDescription +"', sys.user.user_Id FROM sys.user \r\n"
            		+ "WHERE sys.user.userName LIKE '"+ userName +"';";
            
            Statement statement = connection.createStatement();
            int c = statement.executeUpdate(query);
            
            if(c==0) {
            	return false;
            }
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	
	@Override
	public List<Task> showTasks(User selectedUser) throws SQLException {
		
		String userName = selectedUser.getUserName();
		
		final String query = "SELECT sys.task.title, sys.task.description \r\n"
				+ "FROM sys.user JOIN sys.task on sys.user.user_Id=sys.task.user_Id\r\n"
				+ "WHERE sys.user.userName like '" + userName + "'";
		
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        
        List<Task> tasks = new ArrayList<Task>();
        
        while (rs.next()){
        	String taskTitle = rs.getString("title");
            String description = rs.getString("description");
               
            System.out.println(taskTitle + " " + description);
            
            tasks.add(new Task(taskTitle, description));
        }
        
        rs.close();
        return tasks;
	}
	
}