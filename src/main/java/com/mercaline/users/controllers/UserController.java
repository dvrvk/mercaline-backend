package com.mercaline.users.controllers;

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

import com.mercaline.dto.ApiResponse;
import com.mercaline.dto.FavoriteListsResponseDTO;
import com.mercaline.dto.FavoriteProductsInAListResponseDTO;
import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.dto.converter.UserDTOConverter;
import com.mercaline.error.ApiError;
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
	 * @param user the user
	 * @param idList the id list
	 * @param pageable the pageable
	 * @return the response entity
	 */
	@GetMapping("/favorite-products-in-list/{idList}")
	public ResponseEntity<Page<FavoriteProductsInAListResponseDTO>> favoritesProductsInAList(
			@AuthenticationPrincipal UserEntity user,
			@PathVariable Long idList,
			Pageable pageable) {
		return ResponseEntity.ok().body(this.listFavoriteService.findProductsByFavoriteList(user, idList, pageable));
	}

	/**
	 * Delete by product from A list.
	 *
	 * @param user the user
	 * @param idProduct the id product
	 * @param idList the id list
	 * @return the response entity
	 */
	@DeleteMapping("/delete-product/{idProduct}/favorite-list/{idList}")
	public ResponseEntity<Void> deleteByProductFromAList(
			@AuthenticationPrincipal UserEntity user,
			@PathVariable Long idProduct,
			@PathVariable Long idList) {
		this.listFavoriteService.deleteByProductAndFavoriteList(user, idProduct, idList);
		return ResponseEntity.noContent().build();
	}
}
