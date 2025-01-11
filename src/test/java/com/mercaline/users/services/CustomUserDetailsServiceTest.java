/**
 * 
 */
package com.mercaline.users.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mercaline.users.Model.UserEntity;

/**
 * The Class CustomUserDetailsServiceTest.
 *
 * @author caromar
 */
class CustomUserDetailsServiceTest {

	/** The user entity service. */
	@Mock
	private UserEntityService userEntityService;

	/** The custom user details service. */
	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test load user by username user found.
	 */
	@Test
	void testLoadUserByUsername_UserFound() {
		// Configura los datos de prueba
		String username = "testuser";
		UserEntity expectedUser = mock(UserEntity.class);

		when(userEntityService.findUserByUsername(username)).thenReturn(Optional.of(expectedUser));

		// Llamar al método a probar
		UserDetails actualUser = customUserDetailsService.loadUserByUsername(username);

		// Verificar el resultado
		assertNotNull(actualUser);
		assertEquals(expectedUser, actualUser);
	}

	/**
	 * Test load user by username user not found.
	 */
	@Test
	void testLoadUserByUsername_UserNotFound() {
		// Configura los datos de prueba
		String username = "nonexistentuser";

		when(userEntityService.findUserByUsername(username)).thenReturn(Optional.empty());

		// Verificar la excepción
		assertThrows(UsernameNotFoundException.class, () -> {
			customUserDetailsService.loadUserByUsername(username);
		});
	}

	/**
	 * Test load user by id user found.
	 */
	@Test
	void testLoadUserById_UserFound() {
		// Configura los datos de prueba
		Long userId = 1L;
		UserEntity expectedUser = mock(UserEntity.class);

		when(userEntityService.findById(userId)).thenReturn(Optional.of(expectedUser));

		// Llamar al método a probar
		UserDetails actualUser = customUserDetailsService.loadUserById(userId);

		// Verificar el resultado
		assertNotNull(actualUser);
		assertEquals(expectedUser, actualUser);
	}

	/**
	 * Test load user by id user not found.
	 */
	@Test
	void testLoadUserById_UserNotFound() {
		// Configura los datos de prueba
		Long userId = 1L;

		when(userEntityService.findById(userId)).thenReturn(Optional.empty());

		// Verificar la excepción
		assertThrows(UsernameNotFoundException.class, () -> {
			customUserDetailsService.loadUserById(userId);
		});
	}
}
