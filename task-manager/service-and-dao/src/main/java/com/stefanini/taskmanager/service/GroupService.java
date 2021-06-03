package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.util.email.EmailGenerator;
import com.stefanini.taskmanager.util.transaction.Transactional;

/**
 * Interface that provides business logic for {@link GroupTO} entity.
 *
 * @author rarama
 */
@Transactional
public interface GroupService {

  /**
   * Method which receives {@link GroupTO} data as DTO in order to be sent to the {@link GroupDao} and
   * introduced to the database as a new entity.
   *
   * @param group group data transfer object
   * @return boolean value - to show if the operation succeeded
   */
  @EmailGenerator
  boolean createGroup(GroupTO group);

  /**
   * Method which receives the {@link GroupTO} data and {@link UserTO} data as DTOs in order to be sent
   * to the {@link GroupDao} and used to update the database by adding the specified user to given
   * group.
   *
   * @param group group data transfer object
   * @param user user data transfer object
   * @return boolean value - to show if the operation succeeded
   */
  boolean addUserToGroup(GroupTO group, UserTO user);

  /**
   * Method which receives the {@link GroupTO} data and {@link TaskTO} data as DTOs in order to be sent
   * to the {@link GroupDao} and used to update the database by adding the specified task to the
   * members of the given group.
   *
   * @param group - group data transfer object
   * @param task - task data transfer object
   * @return boolean value - to show if the operation succeeded
   */
  boolean addTaskToGroup(GroupTO group, TaskTO task);
}
