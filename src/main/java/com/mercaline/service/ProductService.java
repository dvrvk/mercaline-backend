package com.mercaline.service;

import com.mercaline.dto.ProductRequestDTO;
import com.mercaline.error.exceptions.CategoryNotFoundException;
import com.mercaline.error.exceptions.ProductUnauthorizedAccessException;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.error.exceptions.StatusNotFoundException;
import com.mercaline.model.CategoryEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.model.StatusEntity;
import com.mercaline.repository.ProductRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import static com.mercaline.config.utils.AppConstants.PATH_IMG;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService<ProductEntity, Long, ProductRepository> {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final StatusService statusService;

    // TODO - SUBIR FOTO
//    public ProductEntity create(ProductRequestDTO newProduct, UserEntity user) {
//
//        // Buscar si existe status y category
//        CategoryEntity category = categoryService.findById(newProduct.getCategory())
//                .orElseThrow(CategoryNotFoundException::new);
//
//        StatusEntity status = statusService.findById(newProduct.getStatus())
//                .orElseThrow(StatusNotFoundException::new);
//
//        // Construir productEntity
//        ProductEntity product = ProductEntity
//                .builder()
//                .name(newProduct.getName())
//                .description(newProduct.getDescription())
//                .price(newProduct.getPrice())
//                .urlImage(newProduct.getUrlImage())
//                .status(status)
//                .category(category)
//                .user(user)
//                .build();
//
//        return this.repositorio.save(product);
//    }

    public ProductEntity create2(ProductRequestDTO newProduct, UserEntity user) {

        // Buscar si existe category
        CategoryEntity category = categoryService.findById(newProduct.getCategory())
                .orElseThrow(CategoryNotFoundException::new);
        // Buscar si existe status
        StatusEntity status = statusService.findById(newProduct.getStatus())
                .orElseThrow(StatusNotFoundException::new);

        // Guardar imagen en el servidor
        try {
            // Crear carpeta de fotografias
            Path imagePath = Paths.get(PATH_IMG.concat("/" + user.getId().toString()));
            if(!Files.exists(imagePath)) {
                Files.createDirectories(imagePath);
            }

            String newFileName = "image_" + user.getUsername() + "_" + System.currentTimeMillis() + ".jpg";
            Path copyLocation = imagePath.resolve(newFileName);

            Files.copy(newProduct.getUrlImage().getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            // Construir productEntity
            ProductEntity product = ProductEntity
                    .builder()
                    .name(newProduct.getName())
                    .description(newProduct.getDescription())
                    .price(newProduct.getPrice())
                    .urlImage(copyLocation.toString())
                    .status(status)
                    .category(category)
                    .user(user)
                    .build();

            return this.repositorio.save(product);

        } catch (Exception e) {
            // TODO - REVISAR LOS ERRORES QUE DEVUELVE - TIRARLOS AL CONTROLADOR
            System.out.println(e.getMessage());
        }

        return null;

    }

    public void delete(ProductEntity product, UserEntity user){
        ProductEntity existProduct = comprobarPermisosProduct(product, user);
        this.repositorio.deleteById(existProduct.getId());

    }

//    public ProductEntity edit(ProductRequestDTO editProduct, UserEntity user, Long id) {
//        // Comprobar que el producto pertenece al usuario
//
//        Optional<ProductEntity> myproduct = this.repositorio.findById(id);
//
//        CategoryEntity category = categoryService.findById(editProduct.getCategory())
//                .orElseThrow(CategoryNotFoundException::new);
//
//        StatusEntity status = statusService.findById(editProduct.getStatus())
//                .orElseThrow(StatusNotFoundException::new);
//
//        if(myproduct.isPresent()) {
//            ProductEntity existProduct = myproduct.get();
//
//            if(existProduct.getUser().getId().equals(user.getId())) {
//                existProduct.setName(editProduct.getName());
//                existProduct.setDescription(editProduct.getDescription());
//                existProduct.setPrice(editProduct.getPrice());
//                existProduct.setStatus(status);
//                existProduct.setCategory(category);
//                existProduct.setUrlImage(editProduct.getUrlImage());
//
//                return this.repositorio.save(existProduct);
//            } else {
//                throw new ProductUnauthorizedAccessException(id);
//            }
//        } else {
//            throw new ProductoNotFoundException(id);
//        }
//
//    }

    public Page<ProductEntity> findByCategoryNotUser(Long categoryId, UserEntity user, Pageable pageable) {
        CategoryEntity category = categoryService.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        return this.repositorio.findByUserNotAndCategory(user, category, pageable);
    }

    public Page<ProductEntity> findByUser(UserEntity user, Pageable pageable) {
        return this.repositorio.findByUser(user, pageable);
    }

    public Page<ProductEntity> findOthers(UserEntity user, Pageable pageable) {
        return this.repositorio.findByUserNot(user, pageable);
    }

    public Page<ProductEntity> filterProducts(Long category_id, List<Long> statusList, UserEntity user, Pageable pageable) {
        // Buscar la categoria
        if(category_id != null && category_id != 0) {
            CategoryEntity category = categoryService.findById(category_id)
                    .orElseThrow(CategoryNotFoundException::new);
        }

        if(statusList != null && !statusList.isEmpty()) {
            statusList.forEach(status -> {
                statusService.findById(status)
                        .orElseThrow(StatusNotFoundException::new);
            });
        }
        if(statusList == null || statusList.isEmpty()) {
            return this.repositorio.findProductsByFilterNotStatus(category_id, user.getId(), pageable);
        } else {
            return this.repositorio.findProductsByFilterStatus(category_id, statusList, user.getId(), pageable);
        }

    }

    // PRUEBA CON PRECIO
    public Page<ProductEntity> filterProducts2(Long category_id, List<Long> statusList, UserEntity user,
                                               BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        // Buscar la categoria
        if(category_id != null && category_id != 0) {
            CategoryEntity category = categoryService.findById(category_id)
                    .orElseThrow(CategoryNotFoundException::new);
        }

        if(statusList != null && !statusList.isEmpty()) {
            statusList.forEach(status -> {
                statusService.findById(status)
                        .orElseThrow(StatusNotFoundException::new);
            });
        }

        if(statusList == null || statusList.isEmpty()) {
            return this.repositorio.findProductsByFilterNotStatus2(category_id, user.getId(), minPrice, maxPrice, pageable);
        } else {
            return this.repositorio.findProductsByFilterStatus2(category_id, statusList, user.getId(), minPrice, maxPrice, pageable);
        }

    }


    private ProductEntity comprobarPermisosProduct(ProductEntity product, UserEntity user) {
        Optional<ProductEntity> myproduct = this.repositorio.findById(product.getId());

        if(myproduct.isPresent()) {

            ProductEntity existProduct = myproduct.get();

            if(existProduct.getUser().getId().equals(user.getId())) {
                return existProduct;
            } else {
                throw new ProductUnauthorizedAccessException(product.getId());
            }
        } else {
            throw new ProductoNotFoundException(product.getId());
        }
    }

}
