package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.GroupDao;
import org.hibernate.Session;

public class GroupDaoHibernate implements GroupDao {
    private Session session = null;

    public GroupDaoHibernate(Session session) {
        this.session = session;
    }

    @Override
    public GroupTO createGroup(GroupTO group) {
        return null;
    }

    @Override
    public boolean addUserToGroup(UserTO user, GroupTO group) {
        return false;
    }

    @Override
    public boolean addTaskToGroup(TaskTO task, GroupTO group) {
        return false;
    }
}
