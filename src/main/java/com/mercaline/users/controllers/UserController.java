package com.mercaline.users.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mercaline.dto.*;
import com.mercaline.error.exceptions.FavoriteListException;
import com.mercaline.error.exceptions.FavoriteListNotFoundException;
import com.mercaline.error.exceptions.FavoriteListUnauthorizedException;
import com.mercaline.model.ListFavoriteEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.dto.converter.UserDTOConverter;
import com.mercaline.error.ApiError;
import com.mercaline.model.FavoriteEntity;
import com.mercaline.service.FavoriteService;
import com.mercaline.service.ListFavoriteService;
import com.mercaline.service.ProductService;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.RequestChangePassword;
import com.mercaline.users.dto.RequestUserUpdateDataDTO;
import com.mercaline.users.dto.ResponseUserCompleteDTO;
import com.mercaline.users.dto.ResponseUserSummaryDTO;
import com.mercaline.users.services.UserEntityService;

import lombok.RequiredArgsConstructor;

/**
 * The Class UserController.
 */
@RestController
@RequestMapping("/user")

/**
 * Instantiates a new user controller.
 *
 * @param userEntityService    the user entity service
 * @param productService       the product service
 * @param productoDTOConverter the producto DTO converter
 * @param userDTOConverter     the user DTO converter
 */
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	/** The user entity service. */
	private final UserEntityService userEntityService;

	/** The product service. */
	private final ProductService productService;

	/** The list favorite service. */
	private final ListFavoriteService listFavoriteService;

	private final FavoriteService favoriteService;

	/** The producto DTO converter. */
	private final ProductoDTOConverter productoDTOConverter;

	/** The user DTO converter. */
	private final UserDTOConverter userDTOConverter;

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	@PostMapping("/registrar")
	public ResponseEntity<ResponseUserSummaryDTO> createUser(@Validated @RequestBody UserEntity user) {
		return ResponseEntity
				.ok(userDTOConverter.convertToResponseUserSummaryDTO(this.userEntityService.newUser(user)));
	}

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseUserCompleteDTO> updateUser(@Validated @RequestBody RequestUserUpdateDataDTO user,
			@AuthenticationPrincipal UserEntity userAuth) {
		user.setId(userAuth.getId());
		return ResponseEntity
				.ok(userDTOConverter.convertToResponseUserCompleteDTO(this.userEntityService.updateUser(user)));
	}

	/**
	 * Delete user.
	 *
	 * @param password the user password
	 * @param user     the user
	 * @return the response entity
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> deleteUser(@RequestParam("password") String password,
			@AuthenticationPrincipal UserEntity user) {
		this.userEntityService.passwordMatch(password, user.getPassword());
		this.userEntityService.deleteUser(user);
		return ResponseEntity.ok().build();
	}

	/**
	 * Me.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	@GetMapping("/profile")
	public ResponseEntity<ResponseUserCompleteDTO> me(@AuthenticationPrincipal UserEntity user) {
		return ResponseEntity.ok(userDTOConverter.convertToResponseUserCompleteDTO(user));
	}

	/**
	 * Update password.
	 *
	 * @param requestChangePassword the current and new password
	 * @return the response entity
	 */
	@PutMapping("/change-password")
	public ResponseEntity<?> updatePassword(@RequestBody RequestChangePassword requestChangePassword,
			@AuthenticationPrincipal UserEntity user) {
		if (this.userEntityService.changePassword(requestChangePassword.getPassword(),
				requestChangePassword.getNewPassword(), user)) {
			ApiResponse response = new ApiResponse(HttpStatus.OK, "Contraseña modificada correctamente.");
			return ResponseEntity.ok(response);
		} else {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
					"El servidor no pudo cambiar la contraseña, intenteló más tarde.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
		}
	}

	/**
	 * Other products.
	 *
	 * @param user     the user
	 * @param pageable the pageable
	 * @return the response entity
	 */
	@GetMapping("/products")
	public ResponseEntity<Page<ProductResponseSummaryDTO>> otherProducts(@AuthenticationPrincipal UserEntity user,
			Pageable pageable) {
		Page<ProductResponseSummaryDTO> products = (this.productService.findOthers(user, pageable))
				.map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
		return ResponseEntity.ok().body(products);
	}

	/**
	 * Retrieves a list of favorite lists associated with a product for the
	 * authenticated user.
	 *
	 * This method fetches all the favorite lists containing a particular product
	 * for the currently authenticated user. It returns a list of maps where each
	 * map contains the favorite list ID, list name, and product ID.
	 *
	 * @param user the authenticated user whose favorite lists are being retrieved.
	 * @param id   the product ID for which the favorite lists are being fetched.
	 * @return a ResponseEntity containing the list of favorite lists with the
	 *         product, or an empty list if no favorites are found.
	 */
	@GetMapping("/product-fav-list/{id}")
	public ResponseEntity<?> productFavList(@AuthenticationPrincipal UserEntity user, @PathVariable Long id) {
		List<FavoriteEntity> favorites = this.favoriteService.productInFavoriteList(id, user.getId());
		List<Map<String, Object>> result = favorites.stream().map(favorite -> {
			Map<String, Object> favoriteData = new HashMap<>();
			favoriteData.put("listFavoriteId", favorite.getFavoriteList().getId());
			favoriteData.put("listName", favorite.getFavoriteList().getName());
			favoriteData.put("productId", favorite.getProduct().getId());
			return favoriteData;
		}).collect(Collectors.toList());
		return ResponseEntity.ok().body(result);
	}

	/**
	 * Updates the favorite lists for a specific product.
	 *
	 * This endpoint receives a list of `FavoriteUpdateProdRequestDTO` objects in
	 * the request body containing information about the favorite lists to which
	 * products should be added or removed. The appropriate logic is then executed
	 * to add or remove products from the favorite lists, and a boolean value is
	 * returned to indicate whether the operation was successful.
	 *
	 * @param user The authenticated user performing the operation. It is obtained
	 *             through `@AuthenticationPrincipal`.
	 * @param body The list of `FavoriteUpdateProdRequestDTO` objects containing
	 *             information about the lists and products.
	 * @return ResponseEntity with a boolean value indicating whether the operation
	 *         was successful.
	 */
	@PutMapping("/update-favs")
	public ResponseEntity<?> updateProductFavList(@AuthenticationPrincipal UserEntity user,
			@RequestBody List<FavoriteUpdateProdRequestDTO> body) {
		return ResponseEntity.ok(this.favoriteService.updateProductFavList(user, body));
	}

	/**
	 * Creates a new favorite list for the authenticated user.
	 *
	 * This method receives the name of the new favorite list and creates it for the
	 * currently authenticated user.
	 *
	 * @param user the authenticated user who will own the new favorite list.
	 * @param name the name of the new favorite list to be created.
	 * @return a ResponseEntity containing the ID of the newly created favorite
	 *         list.
	 */
	@PutMapping("/create-list-fav")
	public ResponseEntity<?> createFavoriteList(@AuthenticationPrincipal UserEntity user, @RequestBody String name) {
		return ResponseEntity.ok(this.listFavoriteService.createFavoriteList(user, name));
	}
	
	@DeleteMapping("/delete-list-fav/{id}")
	public ResponseEntity<Void> deleteFavoriteList(@AuthenticationPrincipal UserEntity user, @PathVariable Long id) {
		this.listFavoriteService.deleteFavoriteList(user, id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Edits the list fav.
	 *
	 * @param user the user
	 * @param editListRequest the edit list request
	 * @return the response entity
	 */
	@PutMapping("/edit-list-fav")
	public ResponseEntity<?> editListFav(@AuthenticationPrincipal UserEntity user,
			@RequestBody EditListFavRequest editListRequest) {
		// Comprueba que exista
		ListFavoriteEntity listFav = this.listFavoriteService.findById(editListRequest.getId())
				.orElseThrow(FavoriteListNotFoundException::new);
		// Comprueba que pertenezca al usuario
		if (!Objects.equals(listFav.getUser().getId(), user.getId())) {
			throw new FavoriteListUnauthorizedException(editListRequest.getId());
		}

		try {
			// Edito el nombre
			listFav.setName(editListRequest.getName());
			ListFavoriteEntity output = this.listFavoriteService.edit(listFav);
			// Genero la respuesta
			Map<String, Object> favoriteList = new HashMap<>();
			favoriteList.put("id", output.getId());
			favoriteList.put("nameList", output.getName());
			favoriteList.put("productSize", favoriteService.findByFavoriteList(output));
			return ResponseEntity.ok(favoriteList);
		} catch (DuplicateKeyException e) {
			// Manejo del error
			throw new FavoriteListException();
		}
	}

	/**
	 * Favorites lists products.
	 *
	 * @param user     the user
	 * @param pageable the pageable
	 * @return the response entity
	 */
	@GetMapping("/favorite-product-lists")
	public ResponseEntity<Page<FavoriteListsResponseDTO>> favoritesProducts(@AuthenticationPrincipal UserEntity user,
			Pageable pageable) {
		return ResponseEntity.ok().body(this.listFavoriteService.findByUser(user, pageable));
	}

	/**
	 * Favorites products in A list.
	 *
	 * @param user     the user
	 * @param idList   the id list
	 * @return the response entity
	 */
	@GetMapping("/favorite-products-in-list/{idList}")
	public ResponseEntity<List<FavoriteProductsInAListResponseDTO>> favoritesProductsInAList(
			@AuthenticationPrincipal UserEntity user, @PathVariable Long idList) {
		return ResponseEntity.ok().body(this.listFavoriteService.findProductsByFavoriteList(user, idList));
	}

	/**
	 * Delete by product from A list.
	 *
	 * @param user      the user
	 * @param idProduct the id product
	 * @param idList    the id list
	 * @return the response entity
	 */
	@DeleteMapping("/delete-product/{idProduct}/favorite-list/{idList}")
	public ResponseEntity<Void> deleteByProductFromAList(@AuthenticationPrincipal UserEntity user,
			@PathVariable Long idProduct, @PathVariable Long idList) {
		this.listFavoriteService.deleteByProductAndFavoriteList(user, idProduct, idList);
		return ResponseEntity.noContent().build();
	}
}
