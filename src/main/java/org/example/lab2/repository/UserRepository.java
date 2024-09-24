package org.example.lab2.repository;

import org.example.lab2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByPasswordContaining(String substring);

    Optional<User> findByNameAndPassword(String name, String password);

    List<User> findByContainsASDTrue();
}
