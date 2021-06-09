package com.stefanini.taskmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static com.stefanini.taskmanager.Messages.MAIN;
import static com.stefanini.taskmanager.Messages.OPERATIONS;
import static com.stefanini.taskmanager.operations.DataExtractionUtil.*;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("Program started");

    if (args.length == 0) {
      logger.warn("No given arguments. Finish.");
      return;
    }

    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println(MAIN.message);
      int choice1 = scanner.nextInt();
      switch (choice1) {
        case 1:
          {
            boolean operationsShouldContinue = true;
            while (operationsShouldContinue) {
              System.out.println(OPERATIONS.message);
              int choice2 = scanner.nextInt();
              switch (choice2) {
                case 1:
                  {
                    initNewUser();
                    System.out.println("New operation: create user");
                    break;
                  }
                case 2:
                  {
                    initShowUsers();
                    System.out.println("New operation: show all users");
                    break;
                  }
                case 3:
                  {
                    initNewTask();
                    System.out.println("New operation: add task to user");
                    break;
                  }
                case 4:
                  {
                    initShowTasks();
                    System.out.println("New operation: show tasks of given user");
                    break;
                  }
                case 5:
                  {
                    initCreateGroup();
                    System.out.println("New operation: create group");
                    break;
                  }
                case 6:
                  {
                    initAddUserToGroup();
                    System.out.println("New operation: add user to group");
                    break;
                  }
                case 7:
                  {
                    initAddTaskToGroup();
                    System.out.println("New operation: add task to group");
                    break;
                  }
                case 8:
                  {
                    initNewUserWithTasks();
                    System.out.println("New operation: create user with tasks");
                    break;
                  }
                case 9:
                  {
                    operationsShouldContinue = false;
                    break;
                  }
                default:
                  {
                    break;
                  }
              }
            }
            break;
          }
        case 2:
          {
            printOperations();
            break;
          }
        case 3:
          {
            executeOperations();
            break;
          }
        case 4:
          {
            logger.info("Program finished");
            return;
          }
      }
      System.out.println("-------------------------------");
    }
  }
}
