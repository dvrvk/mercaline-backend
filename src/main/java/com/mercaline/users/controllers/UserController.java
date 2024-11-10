package com.mercaline.users.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.dto.converter.UserDTOConverter;
import com.mercaline.service.ProductService;
import com.mercaline.users.Model.UserEntity;
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
 * @param userEntityService the user entity service
 * @param productService the product service
 * @param productoDTOConverter the producto DTO converter
 * @param userDTOConverter the user DTO converter
 */
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	/** The user entity service. */
	private final UserEntityService userEntityService;
	
	/** The product service. */
	private final ProductService productService;
	
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
	public ResponseEntity<ResponseUserCompleteDTO> updateUser(@Validated @RequestBody RequestUserUpdateDataDTO user, @AuthenticationPrincipal UserEntity userAuth) {
		user.setId(userAuth.getId());
		return ResponseEntity
				.ok(userDTOConverter.convertToResponseUserCompleteDTO(this.userEntityService.updateUser(user)));
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
	 * @param user the user
	 * @return the response entity
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserEntity user) {
		this.userEntityService.deleteUser(user);
		return ResponseEntity.ok().build();
	}

	/**
	 * My products.
	 *
	 * @param user the user
	 * @param pageable the pageable
	 * @return the response entity
	 */
	@GetMapping("/myproducts")
	public ResponseEntity<Page<ProductResponseSummaryDTO>> myProducts(@AuthenticationPrincipal UserEntity user,
			Pageable pageable) {
		Page<ProductResponseSummaryDTO> myProducts = (this.productService.findByUser(user, pageable))
				.map(product -> productoDTOConverter.convertToGetProduct(product, user));
		return ResponseEntity.ok().body(myProducts);
	}

	/**
	 * Other products.
	 *
	 * @param user the user
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

}
