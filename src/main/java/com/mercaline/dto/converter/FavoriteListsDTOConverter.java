package com.mercaline.dto.converter;

import org.springframework.stereotype.Component;

import com.mercaline.dto.FavoriteListsResponseDTO;
import com.mercaline.dto.FavoriteProductsInAListResponseDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.model.ProductEntity;

/**
 * The Class UserDTOConverter.
 */
@Component
public class FavoriteListsDTOConverter {
	
    /**
     * Convert to response user summary DTO.
     *
     * @param user the user
     * @return the response user summary DTO
     */
    public FavoriteListsResponseDTO convertToResponseFavoriteListsSummaryDTO(
    		Long id, String nameList, Integer productSize) {
        return FavoriteListsResponseDTO.builder()
                .id(id)
                .nameList(nameList)
                .productSize(productSize)
                .build();
    }
    
    /**
     * Convert to response favorite products summary DTO.
     *
     * @param favoriteList the favorite list
     * @param product the product
     * @return the favorite products in A list response DTO
     */
    public FavoriteProductsInAListResponseDTO convertToResponseFavoriteProductsSummaryDTO(
    		ListFavoriteEntity favoriteList, ProductEntity product) {
        return FavoriteProductsInAListResponseDTO.builder()
                .id(favoriteList.getId())
                .nameList(favoriteList.getName())
                .products(ProductResponseDTO.builder()
                		.id(product.getId())
                		.name(product.getName())
                		.price(product.getPrice())
                		.category(product.getCategory().getName())
                		.status(product.getStatus().getName())
                		.imageUrl(product.getUrlImage()).build())
                .build();
    }
}
