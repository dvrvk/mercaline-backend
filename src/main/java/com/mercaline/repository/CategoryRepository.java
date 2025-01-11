package com.mercaline.repository;

import com.mercaline.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface CategoryRepository.
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
