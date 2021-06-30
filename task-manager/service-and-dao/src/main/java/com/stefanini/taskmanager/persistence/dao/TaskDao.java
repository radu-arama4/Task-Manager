package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface TaskDao provides methods for performing CRUD operations on {@link TaskTO} entities.
 *
 * @author rarama
 */
@Repository
public interface TaskDao extends CrudRepository<Task, Long> {
  List<Task> findByUser(User user);
}
