package com.stefanini.taskmanager.persistence.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.stefanini.taskmanager.ReadPropertyFile;

public class DButil {
	
	static ReadPropertyFile props = new ReadPropertyFile();
	
	static private final String url = props.getUrl();
    static private final String user = props.getUser();
    static private final String password = props.getPassword();
    
    static private Connection connection = null;
    
    public static Connection connectToDb(){
    	   	
        try {
        	//Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Method for disconnecting from the database
    public static boolean disconnectFromDb() {
        try {
            connection.close();
            return true;
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            return false;
        }
    }
	
}