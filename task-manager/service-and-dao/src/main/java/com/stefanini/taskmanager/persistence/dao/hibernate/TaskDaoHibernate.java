package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import org.hibernate.Session;

import java.util.List;

public class TaskDaoHibernate implements TaskDao {
    private Session session = null;

    public TaskDaoHibernate(Session session) {
        this.session = session;
    }

    @Override
    public TaskTO addTask(TaskTO task, UserTO user) {
        return null;
    }

    @Override
    public List<TaskTO> getTasks(UserTO selectedUser) {
        return null;
    }
}
