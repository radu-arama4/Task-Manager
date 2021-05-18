package com.stefanini.taskmanager.parser;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.User;

public class UserParser {

    private static Logger logger = LogManager.getLogger(UserParser.class);

    private static final String FN = CommandLineVariables.FIRST_NAME.value;
    private static final String LN = CommandLineVariables.LAST_NAME.value;
    private static final String UN = CommandLineVariables.USER_NAME.value;

    public User parseUser(String[] arguments) {

        String firstName = null;
        String lastName = null;
        String userName = null;

        for (String arg : arguments) {
            if (arg.startsWith(FN) && arg.endsWith("'")) {
                firstName = arg.substring(FN.length(), arg.length() - 1);
            } else if (arg.startsWith(LN) && arg.endsWith("'")) {
                lastName = arg.substring(LN.length(), arg.length() - 1);
            } else if (arg.startsWith(UN) && arg.endsWith("'")) {
                userName = arg.substring(UN.length(), arg.length() - 1);
            }
        }

        return new User(firstName, lastName, userName);

    }


}
