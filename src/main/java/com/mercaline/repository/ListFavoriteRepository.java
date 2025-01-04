package com.mercaline.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.users.Model.UserEntity;

/**
 * The Interface ListFavoriteRepository.
 */
public interface ListFavoriteRepository extends JpaRepository<ListFavoriteEntity, Long> {
	
	/**
	 * Find by user.
	 *
	 * @param user the user
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<ListFavoriteEntity> findByUser(UserEntity user, Pageable pageable);

	/**
	 * Find by id and user.
	 *
	 * @param id the id
	 * @param user the user
	 * @return the optional
	 */
	Optional<ListFavoriteEntity> findByIdAndUser(Long id, UserEntity user);
	
	/**
	 * Delete by id and user.
	 *
	 * @param id the id
	 * @param user the user
	 * @return the int
	 */
	@Modifying
	int deleteByIdAndUser(Long id, UserEntity user);
}
