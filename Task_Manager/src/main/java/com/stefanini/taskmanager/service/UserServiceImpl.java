package com.stefanini.taskmanager.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.daoImpl.UserDaoImpl;

public class UserServiceImpl implements UserService{

	UserDao userDao = UserDaoImpl.getInstance();
	
	@Override
	public void createUser(String[] arguments){
		
		if (arguments.length>4){
            System.out.println("Too many arguments!");
            System.out.println(Arrays.toString(arguments));
        }

        String firstName = null;
        String lastName = null;
        String userName = null;

        for(String arg:arguments){
            if(arg.startsWith("-fn='") && arg.endsWith("'")){
                firstName = arg.substring(5, arg.length()-1);
            }
            else if(arg.startsWith("-ln='") && arg.endsWith("'")){
                lastName = arg.substring(5, arg.length()-1);
            }
            else if(arg.startsWith("-un='") && arg.endsWith("'")){
                userName = arg.substring(5, arg.length()-1);
            }
        }

        if(firstName==null || lastName==null || userName==null){
            System.out.println("Information missing!");
        }else if(!checkDuplicatedUserNames(userName)){
            //users.add(new User(firstName, lastName, userName));
            if(userDao.createUser(new User(firstName, lastName, userName))){
                System.out.println("New User " + userName + " added!");
            }
        }else {
            System.out.println("This username already exists!");
        }
	}

	@Override
	public List<User> showAllUsers(){
		
		List<User> users = null;
		
		try {
			users = userDao.getUsers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(User user : users) {
			System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getUserName());
		}
		
		return users;
	}

	public boolean checkDuplicatedUserNames(String userName){
		
		List<User> users = null;
		try {
			users = userDao.getUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        for(User user:users){
            if(user.getUserName().equals(userName)){
                return true;
            }
        }
        
        return false;
    }
	
}