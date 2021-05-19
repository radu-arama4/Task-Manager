package com.stefanini.taskmanager.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.UserDaoImpl;

public class UserServiceImpl implements UserService {

    private UserDao userDao = UserDaoImpl.getInstance();
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public boolean createUser(User user) {

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();

        if (firstName == null || lastName == null || userName == null) {
            logger.warn("Missing information!");
            return false;
        } else {
            User createdUser = userDao.createUser(user);
            if (createdUser != null) {
                logger.info("New user with [first name: " + firstName + "], [last name: " + lastName
                        + "], [username: " + userName + "] added.");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> showAllUsers() {

        List<User> users = null;

        users = userDao.getUsers();

        for (User user : users) {
            logger.info(user.getFirstName() + " " + user.getLastName() + " " + user.getUserName());
        }

        return users;
    }

}
