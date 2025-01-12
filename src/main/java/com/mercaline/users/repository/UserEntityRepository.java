package com.mercaline.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercaline.users.Model.UserEntity;

/**
 * The Interface UserEntityRepository.
 */
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * Find by username.
     *
     * @param name the name
     * @return the optional
     */
    Optional<UserEntity> findByUsername(String name);
}
