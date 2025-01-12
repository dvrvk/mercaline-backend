package com.mercaline.dto.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.ResponseUserCompleteDTO;
import com.mercaline.users.dto.ResponseUserSummaryDTO;

/**
 * The Class UserDTOConverterTest.
 */
class UserDTOConverterTest {

	/** The user DTO converter. */
	private UserDTOConverter userDTOConverter;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		userDTOConverter = new UserDTOConverter();
	}

	/**
	 * Test convert to response user summary DTO.
	 */
	@Test
	void testConvertToResponseUserSummaryDTO() {
		// Configura los datos de prueba
		Long userId = 1L;
		String username = "testuser";	
		String email = "testuser@example.com";
		String tel = "1234567890";

		UserEntity user = new UserEntity();
		user.setId(userId);
		user.setUsername(username);
		user.setEmail(email);
		user.setTel(tel);

		// Llama al método a probar
		ResponseUserSummaryDTO response = userDTOConverter.convertToResponseUserSummaryDTO(user);

		// Verifica el resultado
		assertNotNull(response);
		assertEquals(userId, response.getId());
		assertEquals(username, response.getUsername());
		assertEquals(email, response.getEmail());
		assertEquals(tel, response.getTel());
	}

	/**
	 * Test convert to response user complete DTO.
	 */
	@Test
	void testConvertToResponseUserCompleteDTO() {
		// Configura los datos de prueba
		Long userId = 1L;
		String name = "John";
		String lastname = "Doe";
		String username = "testuser";
		String email = "testuser@example.com";
		String tel = "1234567890";

		UserEntity user = new UserEntity();
		user.setId(userId);
		user.setName(name);
		user.setLastname(lastname);
		user.setUsername(username);
		user.setEmail(email);
		user.setTel(tel);

		// Llama al método a probar
		ResponseUserCompleteDTO response = userDTOConverter.convertToResponseUserCompleteDTO(user);

		// Verifica el resultado
		assertNotNull(response);
		assertEquals(userId, response.getId());
		assertEquals(name, response.getName());
		assertEquals(lastname, response.getLastname());
		assertEquals(username, response.getUsername());
		assertEquals(email, response.getEmail());
		assertEquals(tel, response.getTel());
	}
}
