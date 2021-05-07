package com.stefanini.taskmanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {

	private String user = null;
	private String password = null;
	private String url = null;
	
	public ReadPropertyFile() {
		Properties prop = new Properties();
		FileInputStream ip = null;
		try {
			ip = new FileInputStream("config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		user = prop.getProperty("user");
		password = prop.getProperty("password");
		url = prop.getProperty("url");
		
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

}
