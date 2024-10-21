package com.mercaline.repository;

import com.mercaline.model.ProductEntity;
import com.mercaline.users.Model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByUser(UserEntity user, Pageable pageable);

    Page<ProductEntity> findByUserNot(UserEntity user, Pageable pageable);
}
