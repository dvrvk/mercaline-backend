package com.mercaline.users.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mercaline.error.exceptions.DatabaseConnectionException;
import com.mercaline.error.exceptions.UserNotFoundException;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.RequestUserUpdateDataDTO;
import com.mercaline.users.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

/**
 * The Class UserEntityService.
 */
@Service

/**
 * Instantiates a new user entity service.
 *
 * @param userEntityRepository the user entity repository
 * @param passwordEncoder the password encoder
 */
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, Long, UserEntityRepository> {

	/** The user entity repository. */
	private final UserEntityRepository userEntityRepository;
	
	/** The password encoder. */
	private final PasswordEncoder passwordEncoder;

	/**
	 * Gets the user.
	 *
	 * @param id the id
	 * @return the user
	 */
	public Optional<UserEntity> getUser(Long id) {
		Optional<UserEntity> optionalUser = null;
		try {
			optionalUser = this.userEntityRepository.findById(id);
		} catch (Exception e) {
			throw new DatabaseConnectionException();
		}
		if (optionalUser.isPresent()) {
			return this.userEntityRepository.findById(id);
		} else {
			throw new UserNotFoundException();
		}
	}

	/**
	 * Find user by username.
	 *
	 * @param name the name
	 * @return the optional
	 */
	public Optional<UserEntity> findUserByUsername(String name) {
		return this.userEntityRepository.findByUsername(name);
	}

	/**
	 * New user.
	 *
	 * @param newUser the new user
	 * @return the user entity
	 */
	public UserEntity newUser(UserEntity newUser) {
		try {
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			return this.userEntityRepository.save(newUser);
		} catch (Exception e) {
			throw new DatabaseConnectionException();
		}
	}

	/**
	 * Update user.
	 *
	 * @param updatedUser the updated user
	 * @return the user entity
	 */
	public UserEntity updateUser(RequestUserUpdateDataDTO updatedUser) {
		Optional<UserEntity> optionalUser = null;
		try {
			optionalUser = this.userEntityRepository.findById(updatedUser.getId());
		} catch (Exception e) {
			throw new DatabaseConnectionException();
		}

		if (optionalUser.isPresent()) {
			UserEntity existingUser = optionalUser.get();
			existingUser.setUsername(updatedUser.getUsername());
			existingUser.setName(updatedUser.getName());
			existingUser.setLastname(updatedUser.getLastname());
			existingUser.setTel(updatedUser.getTel());
			existingUser.setEmail(updatedUser.getEmail());
			return this.userEntityRepository.save(existingUser);
		} else {
			throw new UserNotFoundException();
		}
	}
}
