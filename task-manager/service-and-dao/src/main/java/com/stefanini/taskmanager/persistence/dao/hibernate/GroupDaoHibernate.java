package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.InvocationTargetException;

public class GroupDaoHibernate implements GroupDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);

  private static final String GET_USER_BY_USERNAME = "from User where username =: username";
  private static final String GET_GROUP_BY_GROUP_NAME = "from group_ where group_name =: groupName";

  public GroupDaoHibernate(Session session) {
    this.session = session;
  }
  @Override
  public GroupTO createGroup(GroupTO group) {
    Group newGroup = new Group();
    Transaction transaction = session.beginTransaction();

    try {
      BeanUtils.copyProperties(newGroup, group);
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }

    try {
      session.save(newGroup);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    GroupTO returnedGroup = new GroupTO();
    try {
      BeanUtils.copyProperties(newGroup, returnedGroup);
    } catch (InvocationTargetException | IllegalAccessException e) {
      logger.error(e);
    }

    return returnedGroup;
  }

  @Override
  public UserTO addUserToGroup(UserTO user, GroupTO group) {
    Transaction transaction = session.beginTransaction();

    User selectedUser =
        (User)
            session
                .createQuery(GET_USER_BY_USERNAME)
                .setParameter("username", user.getUserName())
                .getSingleResult();

    Group selectedGroup =
        (Group)
            session
                .createQuery(GET_GROUP_BY_GROUP_NAME)
                .setParameter("groupName", group.getGroupName())
                .getSingleResult();

    try {
      selectedGroup.addUser(selectedUser);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    return user;
  }

  @Override
  public TaskTO addTaskToGroup(TaskTO newTask, GroupTO newGroup) {
    Transaction transaction = session.beginTransaction();

    Task task = new Task();
    try {
      BeanUtils.copyProperties(task, newTask);
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }

    Group selectedGroup =
        (Group)
            session
                .createQuery(GET_GROUP_BY_GROUP_NAME)
                .setParameter("groupName", newGroup.getGroupName())
                .list()
                .get(0);

    try {
      task.setGroup(selectedGroup);
      session.save(task);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    TaskTO returnedTask = new TaskTO();
    try{
      BeanUtils.copyProperties(returnedTask, task);
    }catch (Exception e){
      logger.error(e);
      return null;
    }

    return returnedTask;
  }
}
