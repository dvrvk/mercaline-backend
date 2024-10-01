package com.mercaline.controller;

import com.mercaline.dto.ProductDTO;
import com.mercaline.model.ProductEntity;
import com.mercaline.service.ProductService;
import com.mercaline.users.Model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<ProductEntity> newProduct(@RequestBody ProductDTO newProduct, @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.newProduct(newProduct,user));
    }
}
