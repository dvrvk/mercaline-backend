package com.mercaline.repository;

import com.mercaline.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface StatusRepository.
 */
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
