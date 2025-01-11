package com.mercaline.users.services;

import static com.mercaline.config.utils.AppConstants.PATH_IMG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercaline.error.exceptions.DatabaseConnectionException;
import com.mercaline.error.exceptions.DirectoryDeletionException;
import com.mercaline.error.exceptions.FileDeletionException;
import com.mercaline.error.exceptions.PasswordMismatchException;
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
 * @param passwordEncoder      the password encoder
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
	public UserEntity getUser(Long id) {
		try {
			return this.userEntityRepository.findById(id).orElseThrow(UserNotFoundException::new);
		} catch (Exception e) {
			throw new DatabaseConnectionException();
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
		} catch (DataIntegrityViolationException e) {
			throw e;
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

	/**
	 * Change password.
	 *
	 * @param password    the password
	 * @param newPassword the new password
	 * @param user        the user
	 * @return true, if successful
	 */
	public boolean changePassword(String password, String newPassword, UserEntity user) {
		passwordMatch(password, user.getPassword());
		UserEntity userUpdate = UserEntity.builder().id(user.getId()).username(user.getUsername()).name(user.getName())
				.lastname(user.getLastname()).password(passwordEncoder.encode(newPassword)).email(user.getEmail())
				.tel(user.getTel()).build();
		UserEntity savedUser = this.userEntityRepository.save(userUpdate);
		return savedUser.getId().equals(userUpdate.getId());
	}

	/**
	 * Delete user.
	 *
	 * @param user the user
	 */
	@Transactional
	public void deleteUser(UserEntity user) {
		this.delete(user);
		// Delete user directory
		Path userImageDir = Paths.get(PATH_IMG, user.getId().toString());
		deleteDirectory(userImageDir);
	}

	/**
	 * Delete directory.
	 *
	 * @param dirPath the dir path
	 */
	private void deleteDirectory(Path dirPath) {
		try {
			if (Files.exists(dirPath)) {
				// Ordena en orden inverso para borrar archivos primero
				Files.walk(dirPath).sorted((path1, path2) -> path2.compareTo(path1))
						.forEach(path -> {
							try {
								Files.delete(path);
							} catch (IOException e) {
								throw new FileDeletionException();
							}
						});
			}
		} catch (IOException e) {
			throw new DirectoryDeletionException();
		}
	}

	/**
	 * Password match.
	 *
	 * @param password       the password
	 * @param encodePassword the encode password
	 */
	public void passwordMatch(String password, String encodePassword) {
		if (!passwordEncoder.matches(password, encodePassword)) {
			throw new PasswordMismatchException();
		}
	}
}
