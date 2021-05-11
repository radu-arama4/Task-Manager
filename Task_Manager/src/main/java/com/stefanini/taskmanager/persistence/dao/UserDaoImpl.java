package com.stefanini.taskmanager.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.util.DButil;

public class UserDaoImpl implements UserDao {

  private static UserDaoImpl singleInstance = null;
  private Connection connection = DButil.connectToDb();
  private static Logger logger = LogManager.getLogger(GroupDaoImpl.class);

  private UserDaoImpl() {
    logger.info("UserDao instantiated");
  }

  public static UserDaoImpl getInstance() {
    if (singleInstance == null) {
      singleInstance = new UserDaoImpl();
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

      String query = "INSERT INTO user(firstName,lastName,userName) VALUES(?, ?, ?)";

      PreparedStatement pStatement = connection.prepareStatement(query);
      pStatement.setString(1, firstName);
      pStatement.setString(2, lastName);
      pStatement.setString(3, userName);

      pStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<User> getUsers() {

    final String query = "SELECT * FROM user";

    List<User> users = new ArrayList<User>();

    try {
      PreparedStatement pStatement = connection.prepareStatement(query);
      ResultSet rs = pStatement.executeQuery(query);

      while (rs.next()) {
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String userName = rs.getString("userName");

        User newUser = new User(firstName, lastName, userName);
        users.add(newUser);
        rs.close();

        return users;
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }
    return null;
  }
}
