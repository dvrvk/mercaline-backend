package com.mercaline.service;

import com.mercaline.dto.GetProductDTO;
import com.mercaline.dto.ProductDTO;
import com.mercaline.model.ProductEntity;
import com.mercaline.repository.ProductRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.GetUserDto;
import com.mercaline.users.dto.GetUserProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService<ProductEntity, Long, ProductRepository> {

    private final ProductRepository productRepository;

    // TODO - SUBIR FOTO
    public ProductEntity create(ProductDTO newP, UserEntity user) {
        ProductEntity product = ProductEntity
                .builder()
                .nombre(newP.getNombre())
                .descripcion(newP.getDescripcion())
                .precio(newP.getPrecio())
                .imagenUrl(newP.getImagenUrl())
                .estado(newP.getEstado())
                .categoria(newP.getCategoria())
                .usuario(user)
                .build();

        return this.productRepository.save(product);
    }

    public Page<ProductEntity> findByUser(UserEntity user, Pageable pageable) {
        return this.productRepository.findByUsuario(user, pageable);
    }

    public Page<ProductEntity> findOthers(UserEntity user, Pageable pageable) {
        return this.productRepository.findByUsuarioNot(user, pageable);
    }


}
