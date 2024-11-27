package com.mercaline.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mercaline.model.FavoriteEntity;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.model.ProductEntity;

/**
 * The Interface FavoriteRepository.
 */
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
	
	/**
	 * Find by favorite list.
	 *
	 * @param favoriteList the favorite list
	 * @return the list
	 */
	// Busca productos por la lista de favoritos asociada
	List<FavoriteEntity> findByFavoriteList(ListFavoriteEntity favoriteList);
	
	/**
	 * Find by favorite list.
	 *
	 * @param favoriteList the favorite list
	 * @param pageable the pageable
	 * @return the page
	 */
	// Busca productos por la lista de favoritos asociada y paginado
	Page<FavoriteEntity> findByFavoriteList(ListFavoriteEntity favoriteList, Pageable pageable);
	
	/**
	 * Delete by product and favorite list.
	 *
	 * @param product the product
	 * @param favoriteList the favorite list
	 */
	// Busca productos por la lista de favoritos asociada y paginado
	void deleteByProductAndFavoriteList(ProductEntity product, ListFavoriteEntity favoriteList);
}
