package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface UserDao provides methods for performing CRUD operations on {@link UserTO} entities.
 *
 * @author rarama
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {
  User findByUserName(String userName);
}
