package com.mercaline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.users.Model.UserEntity;

public interface ListFavoriteRepository extends JpaRepository<ListFavoriteEntity, Long> {
	
	Page<ListFavoriteEntity> findByUser(UserEntity user, Pageable pageable);
}