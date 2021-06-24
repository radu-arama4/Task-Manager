package com.stefanini.taskmanager.persistence.dao.jdbc;

import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.entity.hibernate.UserHibernate;
import com.stefanini.taskmanager.persistence.entity.jdbc.UserJdbc;
import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@Qualifier("jdbc")
@Scope("singleton")
public class UserDaoJdbc implements UserDao {
  private final Connection connection = DataBaseUtil.connectWithJdbc();
  private static final Logger logger = LogManager.getLogger(UserDaoJdbc.class);

  private static final String INSERT_USER_QUERY =
      "INSERT INTO user(first_name,last_name,username) VALUES(?, ?, ?)";
  private static final String SELECT_USERS_QUERY = "SELECT * FROM user";

  public UserDaoJdbc() {
    logger.info("UserDao instantiated");
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
        return new UserHibernate(userId, firstName, lastName, userName);
      } else {
        return null;
      }
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public Stream<User> getUsers() {
    List<User> users = new ArrayList<>();

    try {
      PreparedStatement statement = connection.prepareStatement(SELECT_USERS_QUERY);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        Long userId = rs.getLong("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String userName = rs.getString("username");

        User newUser = new UserJdbc(userId, firstName, lastName, userName);
        users.add(newUser);
      }
      rs.close();
    } catch (SQLException e) {
      logger.error(e);
    }

    return users.stream();
  }
}
