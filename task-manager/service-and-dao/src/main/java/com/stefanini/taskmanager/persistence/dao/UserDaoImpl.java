package com.stefanini.taskmanager.persistence.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.util.DButil;

public class UserDaoImpl implements UserDao {

    private static UserDao singleInstance = null;
    private Connection connection = DButil.connectToDb();
    private static Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private UserDaoImpl() {
        logger.info("UserDao instantiated");
    }

    public static UserDao getInstance() {
        if (singleInstance == null) {
            singleInstance = new UserDaoImpl();
        }
        return singleInstance;
    }

    @Override
    public User createUser(User newUser) {
        try {

            String firstName = newUser.getFirstName();
            String lastName = newUser.getLastName();
            String userName = newUser.getUserName();

            String query = "INSERT INTO user(first_name,last_name,username) VALUES(?, ?, ?)";

            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, userName);

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            Long id = null;

            if (rs.next()) {
                id = rs.getLong(1);
                return new User(id, firstName, lastName, userName);
            } else {
                return null;
            }
        } catch (SQLException f) {
            logger.error(f);
            return null;
        }
    }

    @Override
    public List<User> getUsers() {

        final String query = "SELECT * FROM user";

        List<User> users = new ArrayList<User>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Long id = rs.getLong("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String userName = rs.getString("username");

                User newUser = new User(id, firstName, lastName, userName);
                users.add(newUser);
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e);
        }

        return users;
    }
}
