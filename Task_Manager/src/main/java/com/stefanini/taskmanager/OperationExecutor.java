package com.stefanini.taskmanager;

import java.util.ArrayList;
import java.util.List;

public class OperationExecutor {

	private final List<Operation> textFileOperations
    = new ArrayList<>();
   
   public void executeOperation(Operation operation) {
       textFileOperations.add(operation);
       operation.execute();
   }
	
}
