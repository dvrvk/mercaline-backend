package com.mercaline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercaline.model.FavoriteEntity;
import com.mercaline.model.ListFavoriteEntity;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
	
	// Busca productos por la lista de favoritos asociada
	List<FavoriteEntity> findByFavoriteList(ListFavoriteEntity favoriteList);
}
