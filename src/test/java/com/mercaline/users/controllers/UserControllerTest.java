package com.mercaline.users.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mercaline.dto.ApiResponse;
import com.mercaline.dto.EditListFavRequest;
import com.mercaline.dto.FavoriteProductsInAListResponseDTO;
import com.mercaline.dto.FavoriteUpdateProdRequestDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.dto.converter.UserDTOConverter;
import com.mercaline.error.ApiError;
import com.mercaline.error.exceptions.FavoriteListException;
import com.mercaline.error.exceptions.FavoriteListNotFoundException;
import com.mercaline.error.exceptions.FavoriteListUnauthorizedException;
import com.mercaline.model.FavoriteEntity;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.service.FavoriteService;
import com.mercaline.service.ListFavoriteService;
import com.mercaline.service.ProductService;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.RequestChangePassword;
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

	/** The list favorite service. */
	@Mock
	private ListFavoriteService listFavoriteService;

	/** The producto DTO converter. */
	@Mock
	private ProductoDTOConverter productoDTOConverter;

	/** The user DTO converter. */
	@Mock
	private UserDTOConverter userDTOConverter;

	/** The favorite service. */
	@Mock
	private FavoriteService favoriteService;

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
     * Test update product fav list.
     */
    @Test
    public void testUpdateProductFavList() {
        // Configura los datos de prueba
        Long userId = 1L;

        UserEntity user = new UserEntity();
        user.setId(userId);

        FavoriteUpdateProdRequestDTO updateRequest1 = new FavoriteUpdateProdRequestDTO(userId, userId, true, false);
        // Configurar los atributos de updateRequest1

        FavoriteUpdateProdRequestDTO updateRequest2 = new FavoriteUpdateProdRequestDTO(userId, userId, false, true);
        // Configurar los atributos de updateRequest2

        List<FavoriteUpdateProdRequestDTO> updateRequests = Arrays.asList(updateRequest1, updateRequest2);

        Boolean updatedFavorites = true;
        // Agregar elementos a updatedFavorites si es necesario

        when(favoriteService.updateProductFavList(user, updateRequests)).thenReturn(updatedFavorites);

        // Llamar al método a probar
        ResponseEntity<?> response = userController.updateProductFavList(user, updateRequests);

        // Verificar el resultado
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody instanceof List);
    }
    
    /**
     * Test create favorite list.
     */
    @Test
    public void testCreateFavoriteList() {
        // Configura los datos de prueba
        Long userId = 1L;
        String listName = "New Favorite List";
        Map<String, Object> createdList = new HashMap<>();
        createdList.put("id", 1L);
        createdList.put("name", listName);

        UserEntity user = new UserEntity();
        user.setId(userId);

        when(listFavoriteService.createFavoriteList(user, listName)).thenReturn(createdList);

        // Llamar al método a probar
        ResponseEntity<?> response = userController.createFavoriteList(user, listName);

        // Verificar el resultado
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof Map);

        @SuppressWarnings("unchecked")
		Map<String, Object> responseMap = (Map<String, Object>) responseBody;
        assertEquals(1L, responseMap.get("id"));
        assertEquals(listName, responseMap.get("name"));
    }
	
    /**
     * Test delete favorite list.
     */
    @Test
    public void testDeleteFavoriteList() {
        // Configura los datos de prueba
        Long userId = 1L;
        Long listId = 2L;

        UserEntity user = new UserEntity();
        user.setId(userId);

        // No necesitamos configurar el comportamiento de listFavoriteService.deleteFavoriteList
        // porque es un método void y queremos verificar que se llame correctamente

        // Llamar al método a probar
        ResponseEntity<Void> response = userController.deleteFavoriteList(user, listId);

        // Verificar el resultado
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verificar que el método deleteFavoriteList se llamó correctamente
        verify(listFavoriteService).deleteFavoriteList(user, listId);
    }
	
    /**
     * Test favorites products in A list.
     */
    @Test
    public void testFavoritesProductsInAList() {
        // Configura los datos de prueba
        Long userId = 1L;
        Long listId = 2L;

        UserEntity user = new UserEntity();
        user.setId(userId);

        FavoriteProductsInAListResponseDTO favoriteProduct1 = new FavoriteProductsInAListResponseDTO();
        // Configurar los atributos de favoriteProduct1

        FavoriteProductsInAListResponseDTO favoriteProduct2 = new FavoriteProductsInAListResponseDTO();
        // Configurar los atributos de favoriteProduct2

        List<FavoriteProductsInAListResponseDTO> favoriteProducts = Arrays.asList(favoriteProduct1, favoriteProduct2);

        when(listFavoriteService.findProductsByFavoriteList(user, listId)).thenReturn(favoriteProducts);

        // Llamar al método a probar
        ResponseEntity<List<FavoriteProductsInAListResponseDTO>> response = userController.favoritesProductsInAList(user, listId);

        // Verificar el resultado
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<FavoriteProductsInAListResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals(favoriteProduct1, responseBody.get(0));
        assertEquals(favoriteProduct2, responseBody.get(1));
    }
	
    /**
     * Test delete by product from A list.
     */
    @Test
    public void testDeleteByProductFromAList() {
        // Configura los datos de prueba
        Long userId = 1L;
        Long productId = 2L;
        Long listId = 3L;

        UserEntity user = new UserEntity();
        user.setId(userId);

        // No necesitamos configurar el comportamiento de listFavoriteService.deleteByProductAndFavoriteList
        // porque es un método void y queremos verificar que se llame correctamente

        // Llamar al método a probar
        ResponseEntity<Void> response = userController.deleteByProductFromAList(user, productId, listId);

        // Verificar el resultado
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verificar que el método deleteByProductAndFavoriteList se llamó correctamente
        verify(listFavoriteService).deleteByProductAndFavoriteList(user, productId, listId);
    }
    
    /**
     * Test edit list fav success.
     */
    @Test
    public void testEditListFav_Success() {
        // Configura los datos de prueba
        Long userId = 1L;
        Long listId = 2L;
        String newListName = "New List Name";

        UserEntity user = new UserEntity();
        user.setId(userId);

        ListFavoriteEntity listFav = new ListFavoriteEntity();
        listFav.setId(listId);
        listFav.setName("Old List Name");
        listFav.setUser(user);

        EditListFavRequest editListRequest = new EditListFavRequest();
        editListRequest.setId(listId);
        editListRequest.setName(newListName);

        when(listFavoriteService.findById(listId)).thenReturn(Optional.of(listFav));
        when(listFavoriteService.edit(any(ListFavoriteEntity.class))).thenReturn(listFav);
        when(favoriteService.findByFavoriteList(listFav)).thenReturn(Collections.emptyList());

        // Llamar al método a probar
        ResponseEntity<?> response = userController.editListFav(user, editListRequest);

        // Verificar el resultado
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof Map);

        @SuppressWarnings("unchecked")
		Map<String, Object> favoriteList = (Map<String, Object>) responseBody;
        assertEquals(listId, favoriteList.get("id"));
        assertEquals(newListName, favoriteList.get("nameList"));
        assertEquals(Collections.emptyList(), favoriteList.get("productSize"));
    }

    /**
     * Test edit list fav list not found.
     */
    @Test
    public void testEditListFav_ListNotFound() {
        // Configura los datos de prueba
        Long listId = 2L;
        EditListFavRequest editListRequest = new EditListFavRequest();
        editListRequest.setId(listId);

        when(listFavoriteService.findById(listId)).thenReturn(Optional.empty());

        // Verificar la excepción
        assertThrows(FavoriteListNotFoundException.class, () -> {
            userController.editListFav(new UserEntity(), editListRequest);
        });
    }

    /**
     * Test edit list fav unauthorized.
     */
    @Test
    public void testEditListFav_Unauthorized() {
        // Configura los datos de prueba
        Long userId = 1L;
        Long otherUserId = 2L;
        Long listId = 3L;

        UserEntity user = new UserEntity();
        user.setId(userId);

        UserEntity otherUser = new UserEntity();
        otherUser.setId(otherUserId);

        ListFavoriteEntity listFav = new ListFavoriteEntity();
        listFav.setId(listId);
        listFav.setUser(otherUser);

        EditListFavRequest editListRequest = new EditListFavRequest();
        editListRequest.setId(listId);

        when(listFavoriteService.findById(listId)).thenReturn(Optional.of(listFav));

        // Verificar la excepción
        assertThrows(FavoriteListUnauthorizedException.class, () -> {
            userController.editListFav(user, editListRequest);
        });
    }

    /**
     * Test edit list fav duplicate key.
     */
    @Test
    public void testEditListFav_DuplicateKey() {
        // Configura los datos de prueba
        Long userId = 1L;
        Long listId = 2L;
        String newListName = "New List Name";

        UserEntity user = new UserEntity();
        user.setId(userId);

        ListFavoriteEntity listFav = new ListFavoriteEntity();
        listFav.setId(listId);
        listFav.setName("Old List Name");
        listFav.setUser(user);

        EditListFavRequest editListRequest = new EditListFavRequest();
        editListRequest.setId(listId);
        editListRequest.setName(newListName);

        when(listFavoriteService.findById(listId)).thenReturn(Optional.of(listFav));
        when(listFavoriteService.edit(any(ListFavoriteEntity.class))).thenThrow(DuplicateKeyException.class);

        // Verificar la excepción
        assertThrows(FavoriteListException.class, () -> {
            userController.editListFav(user, editListRequest);
        });
    }

	/**
	 * Test product fav list.
	 */
	@Test
	public void testProductFavList() {
		// Configura los datos de prueba
		Long userId = 1L;
		Long productId = 2L;
		Long favoriteListId = 3L;
		String listName = "Favoritos";

		UserEntity user = new UserEntity();
		user.setId(userId);

		ListFavoriteEntity favoriteList = new ListFavoriteEntity();
		favoriteList.setId(favoriteListId);
		favoriteList.setName(listName);

		ProductEntity product = new ProductEntity();
		product.setId(productId);

		FavoriteEntity favoriteEntity = new FavoriteEntity();
		favoriteEntity.setFavoriteList(favoriteList);
		favoriteEntity.setProduct(product);

		List<FavoriteEntity> favorites = Arrays.asList(favoriteEntity);

		when(favoriteService.productInFavoriteList(productId, userId)).thenReturn(favorites);

		// Llamar al método a probar
		ResponseEntity<?> response = userController.productFavList(user, productId);

		// Verificar el resultado
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		Object responseBody = response.getBody();
		assertNotNull(responseBody);
		assertTrue(responseBody instanceof List<?>);

		List<?> rawList = (List<?>) responseBody;
		assertFalse(rawList.isEmpty());
		assertTrue(rawList.get(0) instanceof Map);

		List<Map<String, Object>> result = new ArrayList<>();
		for (Object item : rawList) {
			if (item instanceof Map<?, ?>) {
				@SuppressWarnings("unchecked")
				Map<String, Object> mapItem = (Map<String, Object>) item;
				result.add(mapItem);
			}
		}

		assertEquals(1, result.size());

		Map<String, Object> favoriteData = result.get(0);
		assertEquals(favoriteListId, favoriteData.get("listFavoriteId"));
		assertEquals(listName, favoriteData.get("listName"));
		assertEquals(productId, favoriteData.get("productId"));
	}

	/**
	 * Test update password success.
	 */
	@Test
	public void testUpdatePassword_Success() {
		// Mock request y usuario
		RequestChangePassword requestChangePassword = new RequestChangePassword("oldPassword", "newPassword");
		UserEntity user = new UserEntity();

		// Mock del comportamiento del servicio
		when(userEntityService.changePassword("oldPassword", "newPassword", user)).thenReturn(true);

		// Llamar al método a probar
		ResponseEntity<?> response = userController.updatePassword(requestChangePassword, user);

		// Verificar el resultado
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody() instanceof ApiResponse);
		assertEquals("Contraseña modificada correctamente.", ((ApiResponse) response.getBody()).getMensaje());
	}

	/**
	 * Test update password failure.
	 */
	@Test
	public void testUpdatePassword_Failure() {
		// Mock request y usuario
		RequestChangePassword requestChangePassword = new RequestChangePassword("oldPassword", "newPassword");
		UserEntity user = new UserEntity();

		// Mock del comportamiento del servicio
		when(userEntityService.changePassword("oldPassword", "newPassword", user)).thenReturn(false);

		// Llamar al método a probar
		ResponseEntity<?> response = userController.updatePassword(requestChangePassword, user);

		// Verificar el resultado
		assertNotNull(response);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody() instanceof ApiError);
		assertEquals("El servidor no pudo cambiar la contraseña, intenteló más tarde.",
				((ApiError) response.getBody()).getMensaje());
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
		assertNotNull(userController.deleteUser(testUser.getPassword(), testUser));
		Mockito.verify(this.userEntityService, Mockito.times(1)).deleteUser(testUser);
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

	/**
	 * Test favorites products.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testFavoritesProducts() throws Exception {
		Pageable pageable = PageRequest.of(0, 10);
		assertNotNull(userController.favoritesProducts(testUser, pageable));
		Mockito.verify(this.listFavoriteService, Mockito.times(1)).findByUser(testUser, pageable);
	}
}
