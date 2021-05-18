package com.stefanini.taskmanager.operations;

import java.util.ArrayList;
import java.util.List;

public class OperationExecutor {

    private final List<Operation> textFileOperations = new ArrayList<>();

    public void addOperation(Operation operation){
        textFileOperations.add(operation);
    }

    public void executeOperations() {
        for (Operation operation : textFileOperations){
            operation.execute();
        }
    }

}
