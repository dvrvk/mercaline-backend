package com.mercaline.users.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.dto.converter.UserDTOConverter;
import com.mercaline.model.ProductEntity;
import com.mercaline.service.ProductService;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.RequestUserUpdateDataDTO;
import com.mercaline.users.services.UserEntityService;

/**
 * The Class UserControllerTest.
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	/** The user controller. */
	@InjectMocks
	private UserController userController;

	/** The user entity service. */
	@Mock
	private UserEntityService userEntityService;

	/** The product service. */
	@Mock
	private ProductService productService;

	/** The producto DTO converter. */
	@Mock
	private ProductoDTOConverter productoDTOConverter;

	/** The user DTO converter. */
	@Mock
	private UserDTOConverter userDTOConverter;

	/** The test user. */
	private UserEntity testUser;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		testUser = UserEntity.builder().id(1L).username("UsuarioTest").password("password123").name("Test")
				.lastname("Test Test").email("test@domain.com").build();
	}

	/**
	 * Test create user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testCreateUser() throws Exception {
		assertNotNull(userController.createUser(testUser));
		Mockito.verify(this.userEntityService, Mockito.times(1)).newUser(testUser);
	}

	/**
	 * Test update user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testUpdateUser() throws Exception {
		RequestUserUpdateDataDTO user = RequestUserUpdateDataDTO.builder().username("UsuarioTest").name("Test")
				.lastname("Test Test").email("test@domain.com").build();
		UserEntity userAuth = UserEntity.builder().id(1L).build();
		assertNotNull(userController.updateUser(user, userAuth));
		Mockito.verify(this.userEntityService, Mockito.times(1)).updateUser(user);
	}

	/**
	 * Test me.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testMe() throws Exception {
		assertNotNull(userController.me(testUser));
	}

	/**
	 * Test delete user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testDeleteUser() throws Exception {
		assertNotNull(userController.deleteUser(testUser));
		Mockito.verify(this.userEntityService, Mockito.times(1)).delete(testUser);
	}

	/**
	 * Test get my products.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testGetMyProducts() throws Exception {
		Page<ProductEntity> productPage = new PageImpl<>(
				Collections.singletonList(ProductEntity.builder().id(1L).name("Test").description("Test").build()));
		when(productService.findByUser(any(UserEntity.class), any(Pageable.class))).thenReturn(productPage);

		Pageable pageable = PageRequest.of(0, 10);
		assertNotNull(userController.myProducts(testUser, pageable));
		Mockito.verify(this.productService, Mockito.times(1)).findByUser(testUser, pageable);
	}

	/**
	 * Test get other products.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testGetOtherProducts() throws Exception {
		Page<ProductEntity> productPage = new PageImpl<>(
				Collections.singletonList(ProductEntity.builder().id(1L).name("Test").description("Test").build()));
		when(productService.findOthers(any(UserEntity.class), any(Pageable.class))).thenReturn(productPage);

		Pageable pageable = PageRequest.of(0, 10);
		assertNotNull(userController.otherProducts(testUser, pageable));
		Mockito.verify(this.productService, Mockito.times(1)).findOthers(testUser, pageable);
	}
}
