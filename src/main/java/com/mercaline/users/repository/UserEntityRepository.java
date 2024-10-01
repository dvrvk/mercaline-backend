package com.mercaline.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercaline.users.Model.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String name);
}
