package org.chern.manager.repository;

import org.chern.manager.entity.RetailUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetailUserRepository extends CrudRepository<RetailUser, Integer> {
    Optional<RetailUser> findByLogin(String login);
}
