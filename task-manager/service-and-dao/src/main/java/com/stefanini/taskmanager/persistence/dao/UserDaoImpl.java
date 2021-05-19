package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

  private static UserDao singleInstance = null;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

  private static final String INSERT_USER_QUERY =
      "INSERT INTO user(first_name,last_name,username) VALUES(?, ?, ?)";
  private static final String SELECT_USERS_QUERY = "SELECT * FROM user";

  private UserDaoImpl(Connection connection) {
    this.connection = connection;
    logger.info("UserDao instantiated");
  }

  public static UserDao getInstance(Connection connection) {
    if (singleInstance == null) {
      singleInstance = new UserDaoImpl(connection);
    }
    return singleInstance;
  }

  @Override
  public User createUser(User newUser) {
    try {
      String firstName = newUser.getFirstName();
      String lastName = newUser.getLastName();
      String userName = newUser.getUserName();

      PreparedStatement statement =
          connection.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setString(3, userName);

      statement.executeUpdate();
      ResultSet rs = statement.getGeneratedKeys();
      long userId;

      if (rs.next()) {
        userId = rs.getLong(1);
        return new User(userId, firstName, lastName, userName);
      } else {
        return null;
      }
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public List<User> getUsers() {
    List<User> users = new ArrayList<>();

    try {
      PreparedStatement statement = connection.prepareStatement(SELECT_USERS_QUERY);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        Long userId = rs.getLong("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String userName = rs.getString("username");

        User newUser = new User(userId, firstName, lastName, userName);
        users.add(newUser);
      }
      rs.close();
    } catch (SQLException e) {
      logger.error(e);
    }

    return users;
  }
}
