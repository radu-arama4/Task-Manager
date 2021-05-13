package com.stefanini.taskmanager.persistence.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.util.ReadPropertyFile;

public class DButil {

  static ReadPropertyFile props = new ReadPropertyFile();
  private static Logger logger = LogManager.getLogger(DButil.class);

  static private final String url = props.getUrl();
  static private final String user = props.getUser();
  static private final String password = props.getPassword();

  static private Connection connection = null;

  // Method for connecting to the database
  public static Connection connectToDb() {
    try {
      connection = DriverManager.getConnection(url, user, password);
      logger.info("Connected to DataBase " + url);
      return connection;
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  // Method for disconnecting from the database
  public static boolean disconnectFromDb() {
    try {
      connection.close();
      logger.info("Disconnected from DataBase " + url);
      return true;
    } catch (SQLException e) {
      logger.error(e);
      return false;
    }
  }

}
