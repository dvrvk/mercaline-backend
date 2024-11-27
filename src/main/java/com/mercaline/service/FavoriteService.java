package com.mercaline.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mercaline.model.FavoriteEntity;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.repository.FavoriteRepository;
import com.mercaline.service.base.BaseService;

import lombok.RequiredArgsConstructor;

/**
 * The Class FavoriteService.
 */
@Service

/**
 * Instantiates a new favorite service.
 */
@RequiredArgsConstructor
public class FavoriteService extends BaseService<FavoriteEntity, Long, FavoriteRepository> {
	
    /**
     * Find by favorite list.
     *
     * @param favoriteList the favorite list
     * @return the integer
     */
    public Integer findByFavoriteList(ListFavoriteEntity favoriteList) {
        return this.repositorio.findByFavoriteList(favoriteList).size();
    }
    
    /**
     * Find by favorite list.
     *
     * @param favoriteList the favorite list
     * @param pageable the pageable
     * @return the page
     */
    public Page<FavoriteEntity> findByFavoriteList(ListFavoriteEntity favoriteList, Pageable pageable) {
        return this.repositorio.findByFavoriteList(favoriteList, pageable);
    }
    
    /**
     * Delete by product and favorite list.
     *
     * @param product the product
     * @param favoriteList the favorite list
     */
    public void deleteByProductAndFavoriteList(ProductEntity product, ListFavoriteEntity favoriteList) {
        this.repositorio.deleteByProductAndFavoriteList(product, favoriteList);
    }
}
