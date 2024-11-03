/**
 * 
 */
package com.mercaline.users.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mercaline.error.exceptions.UserNotFoundException;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.RequestUserUpdateDataDTO;
import com.mercaline.users.repository.UserEntityRepository;

/**
 * The Class UserEntityServiceTest.
 *
 * @author caromar
 */
@ExtendWith(MockitoExtension.class)
class UserEntityServiceTest {

	/** The user entity service. */
	@InjectMocks
	private UserEntityService userEntityService;

	/** The user entity repository. */
	@Mock
	private UserEntityRepository userEntityRepository;

	/** The password encoder. */
	@Mock
	private PasswordEncoder passwordEncoder;

	/**
	 * Test get user exception.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testGetUserException() throws Exception {
		assertThrows(UserNotFoundException.class, () -> {
			userEntityService.getUser(1L);
		});
		Mockito.verify(this.userEntityRepository, Mockito.times(1)).findById(1L);
	}

	/**
	 * Test get user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testGetUser() throws Exception {
		Optional<UserEntity> user = Optional.of(UserEntity.builder().id(1L).username("UsuarioTest")
				.password("password123").name("Test").lastname("Test Test").email("test@domain.com").build());

		when(userEntityRepository.findById(any(Long.class))).thenReturn(user);

		assertNotNull(userEntityService.getUser(1L));
	}

	/**
	 * Test find user by username.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testFindUserByUsername() throws Exception {
		assertNotNull(userEntityService.findUserByUsername("test"));
		Mockito.verify(this.userEntityRepository, Mockito.times(1)).findByUsername("test");
	}

	/**
	 * Test new user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testNewUser() throws Exception {
		var user = UserEntity.builder().id(1L).username("UsuarioTest").password("password123").name("Test")
				.lastname("Test Test").email("test@domain.com").build();

		when(userEntityRepository.save(any(UserEntity.class))).thenReturn(user);

		assertNotNull(userEntityService.newUser(user));
		Mockito.verify(this.userEntityRepository, Mockito.times(1)).save(user);
	}

	/**
	 * Test update user exception.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testUpdateUserException() throws Exception {
		var user = RequestUserUpdateDataDTO.builder().id(1L).username("UsuarioTest").name("Test").lastname("Test Test")
				.email("test@domain.com").build();
		assertThrows(UserNotFoundException.class, () -> {
			userEntityService.updateUser(user);
		});
	}

	/**
	 * Test update user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testUpdateUser() throws Exception {
		Optional<UserEntity> user = Optional.of(UserEntity.builder().id(1L).username("UsuarioTest")
				.password("password123").name("Test").lastname("Test Test").email("test@domain.com").build());
		var user2 = UserEntity.builder().id(1L).username("UsuarioTest").password("password123").name("Test")
				.lastname("Test Test").email("test@domain.com").build();

		when(userEntityRepository.findById(any(Long.class))).thenReturn(user);
		when(userEntityRepository.save(any(UserEntity.class))).thenReturn(user2);

		assertNotNull(userEntityService.updateUser(RequestUserUpdateDataDTO.builder().id(1L).username("UsuarioTest")
				.name("Test").lastname("Test Test").email("test@domain.com").build()));
	}
}
