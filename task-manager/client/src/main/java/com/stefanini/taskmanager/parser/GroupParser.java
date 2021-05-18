package com.stefanini.taskmanager.parser;

import com.stefanini.taskmanager.dto.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GroupParser {

    private static Logger logger = LogManager.getLogger(TaskParser.class);

    private final static String GN = CommandLineVariables.GROUP_NAME.value;

    public Group parseGroup(String[] arguments) {

        String groupName = null;

        for (String arg : arguments) {
            if (arg.startsWith(GN) && arg.endsWith("'")) {
                groupName = arg.substring(GN.length(), arg.length() - 1);
            }
        }

        return new Group(groupName);
    }

}
