package com.mercaline.controller;

import com.mercaline.dto.GetProductDTO;
import com.mercaline.dto.ProductDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.ProductEntity;
import com.mercaline.service.ProductService;
import com.mercaline.users.Model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;
    private final ProductoDTOConverter productoDTOConverter;

    @GetMapping
    public ResponseEntity<Page<GetProductDTO>> findAll(Pageable pageable) {
        Page<GetProductDTO> products = (this.productService.findAll(pageable))
                .map(product -> productoDTOConverter.convertToGetProduct(product, product.getUsuario()));
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body((this.productService.findById(id))
                .map(this.productoDTOConverter::convertToProductDTO)
                .orElseThrow(()-> new ProductoNotFoundException(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<GetProductDTO> newProduct(@RequestBody ProductDTO newProduct, @AuthenticationPrincipal UserEntity user) {
        GetProductDTO result = productoDTOConverter.convertToGetProduct(this.productService.create(newProduct,user), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GetProductDTO> updateProduct(@RequestBody ProductEntity updateProduct, @AuthenticationPrincipal UserEntity user) {
        ProductEntity product = this.productService.edit(updateProduct,user);
        return ResponseEntity.ok().body(productoDTOConverter.convertToGetProduct(product, user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        ProductEntity productDelete =  this.productService.findById(id)
                .orElseThrow(()-> new ProductoNotFoundException(id));
        productService.delete(productDelete,user);
        return ResponseEntity.noContent().build();
    }






}
