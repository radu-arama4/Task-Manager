package com.stefanini.taskmanager.persistence.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.util.DButil;

public class UserDaoImpl implements UserDao {

	private static UserDaoImpl singleInstance = null;
	private Connection connection = null;
	private static Logger logger = LogManager.getLogger(GroupDaoImpl.class);

	private UserDaoImpl() {
		connection = DButil.connectToDb();
	}

	public static UserDaoImpl getInstance() {
		if (singleInstance == null) {
			singleInstance = new UserDaoImpl();
			logger.info("UserDao instantiated");
		}
		return singleInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean createUser(User newUser) {
		try {
			String firstName = newUser.getFirstName();
			String lastName = newUser.getLastName();
			String userName = newUser.getUserName();

			String query = "INSERT INTO user(firstName,lastName,userName) " + "VALUES(?, ?, ?)";

			PreparedStatement pStatement = connection.prepareStatement(query);
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.setString(3, userName);

			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsers() throws SQLException {

		final String query = "SELECT * FROM user";

		PreparedStatement pStatement = connection.prepareStatement(query);
		ResultSet rs = pStatement.executeQuery(query);

		List<User> users = new ArrayList<User>();

		while (rs.next()) {
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String userName = rs.getString("userName");

			User newUser = new User(firstName, lastName, userName);
			users.add(newUser);

		}
		rs.close();
		return users;
	}

	@Override
	protected void finalize() throws Throwable {
		DButil.disconnectFromDb();
	}

}
