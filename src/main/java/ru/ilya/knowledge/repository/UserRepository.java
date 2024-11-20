package ru.ilya.knowledge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.knowledge.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
