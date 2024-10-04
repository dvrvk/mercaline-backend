package com.mercaline.dto.converter;

import com.mercaline.dto.GetProductDTO;
import com.mercaline.dto.ProductDTO;
import com.mercaline.model.ProductEntity;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.GetUserDto;
import com.mercaline.users.dto.GetUserProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductoDTOConverter {
    public ProductDTO convertToProductDTO(ProductEntity product) {
        return ProductDTO.builder()
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

    public GetProductDTO convertToGetProduct(ProductEntity product, UserEntity user) {
        GetUserProductDto userDTO = GetUserProductDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return GetProductDTO.builder()
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
