package com.stefanini.taskmanager.persistence.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.util.DButil;

public class UserDaoImpl implements UserDao{

	private static UserDaoImpl singleInstance = null;
	private Connection connection = null;
	
	private UserDaoImpl() {
		connection = DButil.connectToDb();
	}
	
	public static UserDaoImpl getInstance() {
		if(singleInstance == null) {
			singleInstance = new UserDaoImpl();
		}
		return singleInstance;
	}
	
	@Override
	public boolean createUser(User newUser) {	
		try {
            String firstName = newUser.getFirstName();
            String lastName = newUser.getLastName();
            String userName = newUser.getUserName();
            String query = "INSERT INTO user(firstName,lastName,userName) " +
                    "VALUES('" + firstName + "','"+ lastName +"','" + userName + "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public List<User> getUsers() throws SQLException {
		
		final String query = "SELECT * FROM user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        List<User> users = new ArrayList<User>();
        
        while (rs.next()){
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String userName = rs.getString("userName");

            User newUser = new User(firstName, lastName, userName);
            users.add(newUser);

//            if(groupName!=null) {
//                for (Group group : groups) {
//                    if (groupName.equals(group.getGroupName())) {
//                        group.addUserToGroup(newUser);
//                    }
//                }
//            }
        }
        rs.close();
        return users;
	}

}
