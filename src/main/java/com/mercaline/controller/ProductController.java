package com.mercaline.controller;

import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.dto.ProductRequestDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.CategoryEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.model.StatusEntity;
import com.mercaline.service.CategoryService;
import com.mercaline.service.ProductService;
import com.mercaline.service.StatusService;
import com.mercaline.users.Model.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductoDTOConverter productoDTOConverter;
    private final CategoryService categoryService;
    private final StatusService statusService;

    // Todos los productos
    @GetMapping
    public ResponseEntity<Page<ProductResponseSummaryDTO>> findAll(Pageable pageable) {
        Page<ProductResponseSummaryDTO> products = (this.productService.findAll(pageable))
                .map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
        return ResponseEntity.ok().body(products);
    }

    // Un poducto determinado por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body((this.productService.findById(id))
                .map(this.productoDTOConverter::convertToProductDTO)
                .orElseThrow(() -> new ProductoNotFoundException(id)));
    }

    // Todas las categorias
    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryEntity>> findAllCategoriesPageable(Pageable pageable) {
        return ResponseEntity.ok().body(this.categoryService.findAll(pageable));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Page<ProductResponseSummaryDTO>> findAllCategoriesPageable(@PathVariable Long id,
                                                                          @AuthenticationPrincipal UserEntity user,
                                                                          Pageable pageable) {
        Page<ProductResponseSummaryDTO> products = (this.productService.findByCategoryNotUser(id, user, pageable))
                .map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
        return ResponseEntity.ok().body(products);
    }

    // Filtrar productos de otros usuarios por category, List<status>
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponseSummaryDTO>> filterProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<Long> status,
            @AuthenticationPrincipal UserEntity user,
            Pageable pageable) {

         Page<ProductResponseSummaryDTO> products = this.productService.filterProducts(categoryId, status, user, pageable)
                 .map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
         return ResponseEntity.ok().body(products);
    }

    @GetMapping("/status")
    public ResponseEntity<List<StatusEntity>> findAllStatus() {
        return ResponseEntity.ok().body(this.statusService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseSummaryDTO> newProduct(@Valid @RequestBody ProductRequestDTO newProduct, @AuthenticationPrincipal UserEntity user) {
        ProductResponseSummaryDTO result = productoDTOConverter.convertToGetProduct(this.productService.create(newProduct, user), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseSummaryDTO> updateProduct(@Valid @RequestBody ProductRequestDTO updateProduct,
                                                                   @AuthenticationPrincipal UserEntity user, @PathVariable Long id) {
        ProductEntity product = this.productService.edit(updateProduct, user, id);
        return ResponseEntity.ok().body(productoDTOConverter.convertToGetProduct(product, user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        ProductEntity productDelete = this.productService.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        productService.delete(productDelete, user);
        return ResponseEntity.noContent().build();
    }


}
