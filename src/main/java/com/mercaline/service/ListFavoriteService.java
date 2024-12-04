package com.mercaline.service;

import com.mercaline.model.FavoriteEntity;
import com.mercaline.repository.FavoriteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercaline.dto.FavoriteListsResponseDTO;
import com.mercaline.dto.FavoriteProductsInAListResponseDTO;
import com.mercaline.dto.converter.FavoriteListsDTOConverter;
import com.mercaline.error.exceptions.FavoriteListNotFoundException;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.repository.ListFavoriteRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * The Class ListFavoriteService.
 */
@Service

/**
 * Instantiates a new list favorite service.
 *
 * @param favoriteService the favorite service
 * @param productService the product service
 * @param favoriteListsDTOConverter the favorite lists DTO converter
 */
@RequiredArgsConstructor
public class ListFavoriteService extends BaseService<ListFavoriteEntity, Long, ListFavoriteRepository> {

	/** The favorite service. */
	private final FavoriteService favoriteService;
	
	/** The product service. */
	private final ProductService productService;

	/** The favorite lists DTO converter. */
	private final FavoriteListsDTOConverter favoriteListsDTOConverter;

	private final FavoriteRepository favoriteRepository;

	/**
	 * Find by user.
	 *
	 * @param user the user
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<FavoriteListsResponseDTO> findByUser(UserEntity user, Pageable pageable) {
		return this.repositorio.findByUser(user, pageable)
				.map(favoriteList -> favoriteListsDTOConverter.convertToResponseFavoriteListsSummaryDTO(
						favoriteList.getId(), favoriteList.getName(),
						favoriteService.findByFavoriteList(favoriteList)));
	}

	/**
	 * Find products by favorite list.
	 *
	 * @param user the user
	 * @param idList the id list
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<FavoriteProductsInAListResponseDTO> findProductsByFavoriteList(UserEntity user, Long idList,
			Pageable pageable) {
		// Buscar las listas del usuario y ver si coinciden el idList pasado por el
		// front
		ListFavoriteEntity listFavoriteEntity = this.repositorio.findByIdAndUser(idList, user)
				.orElseThrow(FavoriteListNotFoundException::new);

		return this.favoriteService.findByFavoriteList(listFavoriteEntity, pageable)
				.map(favoriteList -> favoriteListsDTOConverter.convertToResponseFavoriteProductsSummaryDTO(
						favoriteList.getFavoriteList(), favoriteList.getProduct()));
	}
	
	/**
	 * Delete by product and favorite list.
	 *
	 * @param user the user
	 * @param idProduct the id product
	 * @param idList the id list
	 */
	@Transactional
	public void deleteByProductAndFavoriteList(UserEntity user, Long idProduct, Long idList) {
		// Buscar el producto
		ProductEntity productEntity = this.productService.findById(idProduct)
				.orElseThrow(ProductoNotFoundException::new);
		// Buscar las listas del usuario y ver si coinciden el idList pasado por el
		// front
		ListFavoriteEntity listFavoriteEntity = this.repositorio.findByIdAndUser(idList, user)
				.orElseThrow(FavoriteListNotFoundException::new);
		//Eliminar el producto
		this.favoriteService.deleteByProductAndFavoriteList(productEntity, listFavoriteEntity);
	}


	public boolean isFavorite(Long id, Long userId) {

		boolean result = this.favoriteRepository.existsByUserIdAndProductId(userId, id) > 0;
		return result;
	}

	public List<FavoriteEntity> productInFavoriteList(Long idProduct, Long userId) {
		List<FavoriteEntity> result = this.favoriteRepository.findByProductIdAndFavoriteListUserId(idProduct,userId);
		return result;
	}
}
