package com.test.testing.domain.repository;

import com.test.testing.domain.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByUsername(String username);

    Optional<UserEntity> findUserByEmail(String email);

    Optional<UserEntity> findUserById(Long id);

}

