package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDaoHibernate implements TaskDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(GroupDaoJdbc.class);

  private static final String GET_USER_BY_USERNAME = "from User where username =: username";
  private static final String GET_TASKS_BY_USER_USERNAME =
      "from Task t where t.user.userName =: username";

  public TaskDaoHibernate(Session session) {
    this.session = session;
  }

  @Override
  public TaskTO addTask(TaskTO newTask, UserTO user) {
    Task task = new Task();
    Transaction transaction = session.beginTransaction();

    User selectedUser =
        (User)
            session
                .createQuery(GET_USER_BY_USERNAME)
                .setParameter("username", user.getUserName())
                .getSingleResult();

    try {
      BeanUtils.copyProperties(task, newTask);
    } catch (InvocationTargetException | IllegalAccessException e) {
      logger.error(e);
    }

    try {
      task.setUser(selectedUser);
      session.save(task);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      logger.error(e);
      return null;
    }

    return toTaskTO(task);
  }

  @Override
  public List<TaskTO> addMultipleTasks(List<TaskTO> tasks, UserTO user) {
    User selectedUser =
        (User)
            session
                .createQuery(GET_USER_BY_USERNAME)
                .setParameter("username", user.getUserName())
                .getSingleResult();

    tasks.forEach(
        taskTO -> {
          Task task = new Task();
          try {
            BeanUtils.copyProperties(taskTO, task);
            selectedUser.addTask(task);
          } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error(e);
          }
        });

    return tasks;
  }

  private TaskTO toTaskTO(Task task) {
    TaskTO returnedTask = new TaskTO();

    try {
      BeanUtils.copyProperties(returnedTask, task);
    } catch (InvocationTargetException | IllegalAccessException e) {
      logger.error(e);
    }
    return returnedTask;
  }

  @Override
  public List<TaskTO> getTasks(UserTO selectedUser) {
    List<Task> tasks =
        (List<Task>)
            session
                .createQuery(GET_TASKS_BY_USER_USERNAME)
                .setParameter("username", selectedUser.getUserName())
                .list();

    return tasks.stream().map(this::toTaskTO).collect(Collectors.toList());
  }
}
