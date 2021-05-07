package com.stefanini.taskmanager.persistence.daoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

	@Override
	public boolean addUserToGroup(User user, Group group) {
		try {
            String groupName = group.getGroupName();
            String userName = user.getUserName();

            String query = "UPDATE user SET groupName = '" + groupName +
                    "' WHERE userName LIKE '" + userName + "'";

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
		
		List<User> groupUsers = group.getGroupUsers();

        for(User usr:groupUsers){
            try {
                String taskTitle = task.getTaskTitle();
                String taskDescription = task.getDescription();
                String userName = usr.getUserName();

                String query = "INSERT INTO task(Task_Title,Description,userName) " +
                        "VALUES('" + taskTitle + "','"+ taskDescription +"','" + userName + "')";

                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
	}
	
}
