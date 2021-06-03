package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.util.transaction.Transactional;

import java.util.List;

//TODO documentation
@Transactional
public interface ExtendedService {
    void createUserWithTasks(UserTO user, List<TaskTO> tasks);
}
