package com.mercaline.dto.converter;

import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.model.ProductEntity;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.ResponseUserCompleteDTO;
import com.mercaline.users.dto.ResponseUserSummaryDTO;
import com.mercaline.users.dto.ResponseUserProductDTO;
import org.springframework.stereotype.Component;

/**
 * The Class ProductoDTOConverter.
 */
@Component
public class ProductoDTOConverter {
	
    /**
     * Convert to product DTO.
     *
     * @param product the product
     * @return the product response DTO
     */
    public ProductResponseDTO convertToProductDTO(ProductEntity product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .statusId(product.getStatus().getId())
                .status(product.getStatus().getName())
                .price(product.getPrice())
                .category(product.getCategory().getName())
                .id_category(product.getCategory().getId())
                .imageURL(product.getUrlImage())
                .seller(ResponseUserCompleteDTO.builder()
                        .id(product.getUser().getId())
                        .username(product.getUser().getUsername())
                        .name(product.getUser().getName())
                        .lastname(product.getUser().getLastname())
                        .email(product.getUser().getEmail())
                        .tel(product.getUser().getTel())
                        .build())
                .build();
    }

    /**
     * Convert to get product.
     *
     * @param product the product
     * @param user the user
     * @return the product response summary DTO
     */
    public ProductResponseSummaryDTO convertToGetProduct(ProductEntity product, UserEntity user) {
        ResponseUserProductDTO userDTO = ResponseUserProductDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return ProductResponseSummaryDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .status(product.getStatus().getName())
                .price(product.getPrice())
                .category(product.getCategory().getName())
                .imageUrl(product.getUrlImage())
                .seller(userDTO)
                .build();
    }
}
