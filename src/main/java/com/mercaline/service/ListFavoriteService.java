package com.mercaline.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mercaline.dto.FavoriteListsResponseDTO;
import com.mercaline.dto.converter.FavoriteListsDTOConverter;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.repository.ListFavoriteRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListFavoriteService extends BaseService<ListFavoriteEntity, Long, ListFavoriteRepository> {

	private final FavoriteService favoriteService;

	private final FavoriteListsDTOConverter favoriteListsDTOConverter;

	public Page<FavoriteListsResponseDTO> findByUser(UserEntity user, Pageable pageable) {
		return this.repositorio.findByUser(user, pageable)
				.map(favoriteList -> favoriteListsDTOConverter.convertToResponseFavoriteListsSummaryDTO(
						favoriteList.getId(), favoriteList.getName(),
						favoriteService.findByFavoriteList(favoriteList)));
	}
}
