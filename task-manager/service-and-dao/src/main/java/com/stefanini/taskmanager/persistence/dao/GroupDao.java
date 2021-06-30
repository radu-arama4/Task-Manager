package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.persistence.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface TaskDao provides methods for performing CRUD operations on {@link GroupTO} entities.
 *
 * @author rarama
 */
@Repository
public interface GroupDao extends CrudRepository<Group, Long> {
  Group findByGroupName(String groupName);
}
