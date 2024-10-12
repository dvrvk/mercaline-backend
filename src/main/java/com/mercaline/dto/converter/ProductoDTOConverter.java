package com.mercaline.dto.converter;

import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.model.ProductEntity;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.GetUserDto;
import com.mercaline.users.dto.GetUserProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductoDTOConverter {
    public ProductResponseDTO convertToProductDTO(ProductEntity product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .nombre(product.getNombre())
                .descripcion(product.getDescripcion())
                .estado(product.getEstado())
                .precio(product.getPrecio())
                .categoria(product.getCategoria())
                .imagenUrl(product.getImagenUrl())
                .vendedor(GetUserDto.builder()
                        .username(product.getUsuario().getUsername())
                        .email(product.getUsuario().getEmail())
                        .tel(product.getUsuario().getTel())
                        .build())
                .build();
    }

    public ProductResponseSummaryDTO convertToGetProduct(ProductEntity product, UserEntity user) {
        GetUserProductDto userDTO = GetUserProductDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return ProductResponseSummaryDTO.builder()
                .id(product.getId())
                .nombre(product.getNombre())
                .descripcion(product.getDescripcion())
                .estado(product.getEstado())
                .precio(product.getPrecio())
                .categoria(product.getCategoria())
                .imagenUrl(product.getImagenUrl())
                .vendedor(userDTO)
                .build();
    }
}
