package com.stefanini.taskmanager.operations;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.categories.task.AddTaskToUserOperation;
import com.stefanini.taskmanager.operations.categories.user.CreateUserOperation;
import com.stefanini.taskmanager.operations.categories.user.ShowAllUsersOperation;
import com.stefanini.taskmanager.operations.execution.OperationExecutor;
import com.stefanini.taskmanager.util.ApplicationManager;
import org.junit.Before;
import org.junit.Test;

public class OperationExecutorTest {
  OperationExecutor operationExecutor = OperationExecutor.getInstance();

  @Before
  public void init(){
    ApplicationManager.initApp();
  }

  @Test
  public void divideOperations() {
    operationExecutor.addOperation(
        new AddTaskToUserOperation(
            new TaskTO("dsa", "fadd"), new UserTO("wai3", "fd", "pechea23")));
    operationExecutor.addOperation(new ShowAllUsersOperation());
    operationExecutor.addOperation(new ShowAllUsersOperation());
    operationExecutor.addOperation(new ShowAllUsersOperation());
    operationExecutor.addOperation(new CreateUserOperation(new UserTO("wai3", "fd", "pechea23")));
    operationExecutor.executeOperations();
  }
}
