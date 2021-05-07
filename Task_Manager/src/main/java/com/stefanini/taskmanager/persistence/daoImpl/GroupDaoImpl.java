package com.stefanini.taskmanager.persistence.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.util.DButil;

public class GroupDaoImpl implements GroupDao {

	private static GroupDaoImpl singleInstance = null;
	Connection connection = null;
	
	private GroupDaoImpl() {
		connection = DButil.connectToDb();
	}
	
	static public GroupDaoImpl getInstance() {
		if(singleInstance == null) {
			singleInstance = new GroupDaoImpl();
		}
		return singleInstance;
	}
	
	@Override
	public boolean createGroup(Group group) {
		try {
            String groupName = group.getGroupName();
            String query = "INSERT INTO sys.group(groupName) " +
                    "VALUES('" + groupName + "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
	}

	private int getIdByGroupName(String groupName) {
		try {
			
			String query = "select sys.group.group_Id as group_Id \r\n"
					+ "from sys.group\r\n"
					+ "where sys.group.groupName LIKE '" + groupName + "';";
			Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            int ID = -1;
            
            while(rs.next()) {
            	String id = rs.getString("group_Id");
            	ID = Integer.parseInt(id);
            	break;
            }
            return ID;
            
		} catch (SQLException e) {
			return -1;
		}
		
	}
	
	@Override
	public boolean addUserToGroup(User user, Group group) {
		try {
            String groupName = group.getGroupName();
            String userName = user.getUserName();

            int ID = getIdByGroupName(groupName);
            
            if(ID==-1) {
    			return false;
    		}
            
            System.out.println(ID);
            
            String query = "UPDATE sys.user SET sys.user.group_Id = " + ID +
                    " WHERE sys.user.userName LIKE '" + userName + "'";

            System.out.println(query);
            
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
	}

	@Override
	public boolean addTaskToGroup(Task task, Group group) {

		int ID = getIdByGroupName(group.getGroupName());
		
		if(ID==-1) {
			return false;
		}
            try {
                String taskTitle = task.getTaskTitle();
                String taskDescription = task.getDescription();

                String query = "INSERT INTO sys.task(title,description,user_Id)\r\n"
                		+ "SELECT '" + taskTitle + "', '" + taskDescription + "', sys.user.user_Id\r\n"
                		+ "FROM sys.user WHERE sys.user.group_Id = " + ID + ";";

                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }

        return true;
	}
	
}
