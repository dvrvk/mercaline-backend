package com.mercaline.service;

import com.mercaline.dto.ProductDTO;
import com.mercaline.error.exceptions.ProductUnauthorizedAccessException;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.ProductEntity;
import com.mercaline.repository.ProductRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;
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

        return this.repositorio.save(product);
    }

    public void delete(ProductEntity product, UserEntity user){
        ProductEntity existProduct = comprobarPermisosProduct(product, user);
        this.repositorio.deleteById(existProduct.getId());

    }

    public ProductEntity edit(ProductEntity product, UserEntity user) {
        // Comprobar que el producto pertenece al usuario
        Optional<ProductEntity> myproduct = this.repositorio.findById(product.getId());

        if(myproduct.isPresent()) {
            ProductEntity existProduct = myproduct.get();

            if(existProduct.getUsuario().getId().equals(user.getId())) {
                existProduct.setNombre(product.getNombre());
                existProduct.setDescripcion(product.getDescripcion());
                existProduct.setPrecio(product.getPrecio());
                existProduct.setEstado(product.getEstado());
                existProduct.setCategoria(product.getCategoria());
                existProduct.setImagenUrl(product.getImagenUrl());

                return this.repositorio.save(existProduct);
            } else {
                throw new ProductUnauthorizedAccessException(product.getId());
            }
        } else {
            throw new ProductoNotFoundException(product.getId());
        }

    }

    public Page<ProductEntity> findByUser(UserEntity user, Pageable pageable) {
        return this.repositorio.findByUsuario(user, pageable);
    }

    public Page<ProductEntity> findOthers(UserEntity user, Pageable pageable) {
        return this.repositorio.findByUsuarioNot(user, pageable);
    }


    private ProductEntity comprobarPermisosProduct(ProductEntity product, UserEntity user) {
        Optional<ProductEntity> myproduct = this.repositorio.findById(product.getId());

        if(myproduct.isPresent()) {

            ProductEntity existProduct = myproduct.get();

            if(existProduct.getUsuario().getId().equals(user.getId())) {
                return existProduct;
            } else {
                throw new ProductUnauthorizedAccessException(product.getId());
            }
        } else {
            throw new ProductoNotFoundException(product.getId());
        }
    }

}
