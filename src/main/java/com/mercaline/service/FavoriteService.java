package com.mercaline.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercaline.dto.FavoriteUpdateProdRequestDTO;
import com.mercaline.error.exceptions.FavoriteListNotFoundException;
import com.mercaline.model.FavoriteEntity;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.repository.FavoriteRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;

import lombok.RequiredArgsConstructor;

/**
 * The Class FavoriteService.
 */
@Service

/**
 * Instantiates a new favorite service.
 */
@RequiredArgsConstructor
public class FavoriteService extends BaseService<FavoriteEntity, Long, FavoriteRepository> {
	
    /**
     * Find by favorite list.
     *
     * @param favoriteList the favorite list
     * @return the integer
     */
    public Integer findByFavoriteListSize(ListFavoriteEntity favoriteList) {
        return this.repositorio.findByFavoriteList(favoriteList).size();
    }
    
    /**
     * Find by favorite list.
     *
     * @param favoriteList the favorite list
     * @param pageable the pageable
     * @return the page
     */
    public List<FavoriteEntity> findByFavoriteList(ListFavoriteEntity favoriteList) {
        return this.repositorio.findByFavoriteList(favoriteList);
    }
    
    /**
     * Delete by product and favorite list.
     *
     * @param product the product
     * @param favoriteList the favorite list
     */
    public void deleteByProductAndFavoriteList(ProductEntity product, ListFavoriteEntity favoriteList) {
        this.repositorio.deleteByProductAndFavoriteList(product, favoriteList);
    }
	
	/**
	 * Checks if is favorite.
	 *
	 * @param id the id
	 * @param userId the user id
	 * @return true, if is favorite
	 */
	public boolean isFavorite(Long id, Long userId) {
		return this.repositorio.existsByUserIdAndProductId(userId, id) > 0;
	}

	/**
	 * Product in favorite list.
	 *
	 * @param idProduct the id product
	 * @param userId the user id
	 * @return the list
	 */
	public List<FavoriteEntity> productInFavoriteList(Long idProduct, Long userId) {
		List<FavoriteEntity> list = this.repositorio.findByProductIdAndFavoriteListUserId(idProduct, userId);
		return list;
	}

	/**
	 * Update product fav list.
	 *
	 * @param user the user
	 * @param body the body
	 * @return true, if successful
	 */
	@Transactional
	public boolean updateProductFavList(UserEntity user, List<FavoriteUpdateProdRequestDTO> body) {
		try {
			body.stream().forEach(favList -> {
				if (favList.isAddProductList()) {
					int rowsAffected = this.repositorio.addProductIdToListId(favList.getIdList(), favList.getIdProduct(), user.getId());
					if (rowsAffected == 0) {
	                    throw new FavoriteListNotFoundException("No se pudo insertar el producto en la lista.");
	                }
				} else if (favList.isDeleteProductList()) {
					int rowsAffected = this.repositorio.deleteProductIdToListId(favList.getIdList(), favList.getIdProduct(),
							user.getId());
					if (rowsAffected == 0) {
	                    throw new FavoriteListNotFoundException("No se pudo eliminar el producto en la lista.");
	                }
				}
			});
			return true;
		} catch (Exception e) {
			throw new FavoriteListNotFoundException("Error actualizando las listas de favoritos");
		}
	}
}
