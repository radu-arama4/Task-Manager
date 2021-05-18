package com.stefanini.taskmanager.parser;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.Task;

public class TaskParser {

    private static Logger logger = LogManager.getLogger(TaskParser.class);

    private static final String TT = CommandLineVariables.TASK_TITLE.value;
    private static final String TD = CommandLineVariables.TASK_DESCRIPTION.value;

    public Task parseTask(String[] arguments) {

        String taskTitle = null;
        String taskDescription = null;

        StringBuilder taskT = new StringBuilder();
        StringBuilder taskD = new StringBuilder();
        String prev = "";

        for (String arg : arguments) {
            if (arg.startsWith(TT) && arg.endsWith("'")) {
                taskTitle = arg.substring(TT.length() + 1, arg.length() - 1);
            } else if (arg.startsWith(TT)) {
                prev = TT;
                taskT.append(arg, TT.length() + 1, arg.length());
                taskT.append(" ");
            } else if (prev.equals(TT)) {
                if (arg.endsWith("'")) {
                    taskT.append(arg, 0, arg.length() - 1);
                    taskTitle = taskT.toString();
                    prev = "";
                } else {
                    taskT.append(arg).append(" ");
                }
            } else if (arg.startsWith(TD) && arg.endsWith("'")) {
                taskDescription = arg.substring(TD.length() + 1, arg.length() - 1);
            } else if (arg.startsWith(TD)) {
                prev = TD;
                taskD.append(arg, TD.length() + 1, arg.length());
                taskD.append(" ");
            } else if (prev.equals(TD)) {
                if (arg.endsWith("'")) {
                    taskD.append(arg, 0, arg.length() - 1);
                    taskDescription = taskD.toString();
                    prev = "";
                } else {
                    taskD.append(arg).append(" ");
                }
            }
        }

        if (taskTitle == null && taskDescription == null) {
            return null;
        }

        return new Task(taskTitle, taskDescription);
    }
}
