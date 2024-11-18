package com.mercaline.service;

import org.springframework.stereotype.Service;

import com.mercaline.model.FavoriteEntity;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.repository.FavoriteRepository;
import com.mercaline.service.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService extends BaseService<FavoriteEntity, Long, FavoriteRepository> {
	
    public Integer findByFavoriteList(ListFavoriteEntity favoriteList) {
        return this.repositorio.findByFavoriteList(favoriteList).size();
    }
}
