package com.stefanini.taskmanager.parser;

public enum CommandLineVariables {
    FIRST_NAME("-fn'"),
    LAST_NAME("-ln'"),
    USER_NAME("-un'"),
    TASK_TITLE("-tt'"),
    TASK_DESCRIPTION("-td'"),
    GROUP_NAME("-gn'");

    public final String value;

    CommandLineVariables(String value) {
        this.value = value;
    }
}
