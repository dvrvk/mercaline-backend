package com.mercaline.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	/**
	 * Exists by user id and product id.
	 *
	 * @param userId the user id
	 * @param productId the product id
	 * @return the integer
	 */
	@Query(value = "SELECT COUNT(*) > 0 AS es_favorito " +
	"FROM favourites f " +
	"JOIN list_fav lf ON f.list_fav_id = lf.id " +
	"WHERE lf.user_id = :userId AND f.product_id = :productId",
	nativeQuery = true)
	Integer existsByUserIdAndProductId(@Param("userId") Long userId,
															  @Param("productId") Long productId);

	/**
	 * Find by product id and favorite list user id.
	 *
	 * @param productId the product id
	 * @param userId the user id
	 * @return the list
	 */
	// Buscar las listas de favoritos que contienen un producto por ID de producto y ID de usuario
	@Query("SELECT f FROM FavoriteEntity f " +
			"JOIN FETCH f.favoriteList fl " +
			"JOIN FETCH f.product p " +
			"WHERE f.product.id = :productId AND fl.user.id = :userId")
	List<FavoriteEntity> findByProductIdAndFavoriteListUserId(
			@Param("productId") Long productId, @Param("userId") Long userId);
	
	/**
	 * Adds the product id to list id.
	 *
	 * @param listId the list id
	 * @param productId the product id
	 * @param userId the user id
	 * @return the int
	 */
	@Modifying
	@Query(value = "INSERT INTO favourites (list_fav_id, product_id) "
			+ "SELECT lf.id, :product_id "
			+ "FROM list_fav lf "
			+ "WHERE lf.id = :list_fav_id "
			+ "AND lf.user_id = :user_id",
			nativeQuery = true)
	int addProductIdToListId(@Param("list_fav_id") Long listId,
			@Param("product_id") Long productId, @Param("user_id") Long userId);
	
	/**
	 * Delete product id to list id.
	 *
	 * @param listId the list id
	 * @param productId the product id
	 * @param userId the user id
	 * @return the int
	 */
	@Modifying
	@Query(value = "DELETE FROM favourites "
			+ "WHERE id = (SELECT f.id FROM favourites f "
			+ "INNER JOIN list_fav lf ON f.list_fav_id = lf.id "
			+ "WHERE f.product_id = :product_id "
			+ "AND lf.id = :list_fav_id "
			+ "AND lf.user_id = :user_id)",
			nativeQuery = true)
	int deleteProductIdToListId(@Param("list_fav_id") Long listId,
			@Param("product_id") Long productId, @Param("user_id") Long userId);

}
