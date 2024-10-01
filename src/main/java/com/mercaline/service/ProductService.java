package com.mercaline.service;

import com.mercaline.dto.ProductDTO;
import com.mercaline.model.ProductEntity;
import com.mercaline.repository.ProductRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService<ProductEntity, Long, ProductRepository> {

    private final ProductRepository productRepository;

    // TODO - SUBIR FOTO - HACER QUE NO DEVUELVA TODOS LOS DATOS DEL USUARIO (DTO)
    public ProductEntity newProduct(ProductDTO newP, UserEntity user) {
        ProductEntity product = ProductEntity
                .builder()
                .nombre(newP.getNombre())
                .descripcion(newP.getDescripcion())
                .precio(newP.getPrecio())
                .imagenUrl(newP.getImagen())
                .estado(newP.getEstado())
                .categoria(newP.getCategoria())
                .usuario(user)
                .build();
        return this.productRepository.save(product);
    }
}
