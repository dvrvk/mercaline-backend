package com.mercaline.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercaline.dto.FavoriteListsResponseDTO;
import com.mercaline.dto.FavoriteProductsInAListResponseDTO;
import com.mercaline.dto.converter.FavoriteListsDTOConverter;
import com.mercaline.error.exceptions.FavoriteListException;
import com.mercaline.error.exceptions.FavoriteListNotFoundException;
import com.mercaline.error.exceptions.ProductUnauthorizedAccessException;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.repository.ListFavoriteRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;

import lombok.RequiredArgsConstructor;

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

	/**
	 * Find by user.
	 *
	 * @param user     the user
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<FavoriteListsResponseDTO> findByUser(UserEntity user, Pageable pageable) {
		return this.repositorio.findByUser(user, pageable)
				.map(favoriteList -> favoriteListsDTOConverter.convertToResponseFavoriteListsSummaryDTO(
						favoriteList.getId(), favoriteList.getName(),
						favoriteService.findByFavoriteList(favoriteList).size()));
	}

	/**
	 * Find products by favorite list.
	 *
	 * @param user     the user
	 * @param idList   the id list
	 * @param pageable the pageable
	 * @return the page
	 */
	public List<FavoriteProductsInAListResponseDTO> findProductsByFavoriteList(UserEntity user, Long idList) {
		// Buscar las listas del usuario y ver si coinciden el idList pasado por el
		// front
		ListFavoriteEntity listFavoriteEntity = this.repositorio.findByIdAndUser(idList, user)
				.orElseThrow(FavoriteListNotFoundException::new);
		
		List<FavoriteProductsInAListResponseDTO> output = new ArrayList<>();
		this.favoriteService.findByFavoriteList(listFavoriteEntity)
				.forEach(favoriteList -> output.add(favoriteListsDTOConverter.convertToResponseFavoriteProductsSummaryDTO(
						favoriteList.getFavoriteList(), favoriteList.getProduct())));
		return output;
	}
	
	/**
	 * Creates the favorite list.
	 *
	 * @param user the user
	 * @param name the name
	 * @return the map
	 */
	@Transactional(rollbackFor = { IOException.class, RuntimeException.class })
	public Map<String, Object> createFavoriteList(UserEntity user, String name) {
		Map<String, Object> favoriteList = new HashMap<>();
		// Comprueba que esa lista no existe con el mismo nombre para el usuario
		try {
		    // Intento de inserción
			ListFavoriteEntity output = this.repositorio.save(ListFavoriteEntity.builder().name(name).user(user).build());
			favoriteList.put("id", output.getId());
			favoriteList.put("nameList", output.getName());
			favoriteList.put("productSize", favoriteService.findByFavoriteList(output));
		} catch (DuplicateKeyException e) {
		    // Manejo del error
			throw new FavoriteListException();
		}
		return favoriteList;
	}
	
	@Transactional(rollbackFor = { IOException.class, RuntimeException.class })
	public void deleteFavoriteList(UserEntity user, Long id) {
		try {
			this.repositorio.deleteByIdAndUser(id, user);
		} catch (Exception e) {
			throw new ProductUnauthorizedAccessException(id);
		}
		
	}

	/**
	 * Delete by product and favorite list.
	 *
	 * @param user      the user
	 * @param idProduct the id product
	 * @param idList    the id list
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
		// Eliminar el producto
		this.favoriteService.deleteByProductAndFavoriteList(productEntity, listFavoriteEntity);
	}
}
