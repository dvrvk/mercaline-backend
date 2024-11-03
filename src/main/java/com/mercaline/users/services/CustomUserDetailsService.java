package com.mercaline.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The Class CustomUserDetailsService.
 */
@Service("userDetailsService")

/**
 * Instantiates a new custom user details service.
 *
 * @param userEntityService the user entity service
 */
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	/** The user entity service. */
	private final UserEntityService userEntityService;

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userEntityService.findUserByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("Usuario con nombre:  " + username + " no encontrado."));
	}

	/**
	 * Load user by id.
	 *
	 * @param id the id
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		return userEntityService.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario con ID: " + id + " no encontrado"));

	}
}
