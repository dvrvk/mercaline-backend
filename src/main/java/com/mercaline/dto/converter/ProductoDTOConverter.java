package com.mercaline.dto.converter;

import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.model.ProductEntity;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.ResponseUserSummaryDTO;
import com.mercaline.users.dto.ResponseUserProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductoDTOConverter {
    public ProductResponseDTO convertToProductDTO(ProductEntity product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .nombre(product.getName())
                .descripcion(product.getDescription())
                .estado(product.getStatus().getName())
                .id_status(product.getStatus().getId())
                .precio(product.getPrice())
                .categoria(product.getCategory().getName())
                .id_category(product.getCategory().getId())
                .imagenUrl(product.getUrlImage())
                .vendedor(ResponseUserSummaryDTO.builder()
                        .id(product.getUser().getId())
                        .username(product.getUser().getUsername())
                        .email(product.getUser().getEmail())
                        .tel(product.getUser().getTel())
                        .build())
                .build();
    }

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
