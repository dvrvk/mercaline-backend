package com.mercaline.users.controllers;

import com.mercaline.dto.GetProductDTO;
import com.mercaline.dto.ProductDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.service.ProductService;
import com.mercaline.users.dto.GetUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.services.UserEntityService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    
    private final UserEntityService userEntityService;
    private final ProductService productService;
    private final ProductoDTOConverter productoDTOConverter;

    @PostMapping("/registrar")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userEntityService.newUser(user);
    }

    @GetMapping("/me")
    public GetUserDto me(@AuthenticationPrincipal UserEntity user) {
        GetUserDto userResp = new GetUserDto();
        return GetUserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .tel(user.getTel())
                .build();
    }

    @GetMapping("/myproducts")
    public ResponseEntity<Page<GetProductDTO>> myProducts(@AuthenticationPrincipal UserEntity user, Pageable pageable) {
        Page<GetProductDTO> myProducts = (this.productService.findByUser(user, pageable))
                .map(product -> productoDTOConverter.convertToGetProduct(product, user));
        return ResponseEntity.ok().body(myProducts);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<GetProductDTO>> otherProducts(@AuthenticationPrincipal UserEntity user, Pageable pageable) {
        Page<GetProductDTO> products = (this.productService.findOthers(user, pageable))
                .map(product -> productoDTOConverter.convertToGetProduct(product, product.getUsuario()));
        return ResponseEntity.ok().body(products);
    }

//    @PutMapping("/product/{id}")
//    public ResponseEntity<GetProductDTO> updateProduct(@RequestBody ProductDTO newProduct, @AuthenticationPrincipal UserEntity user) {
//        return ResponseEntity.status(HttpStatus.).body(this.productService.create(newProduct,user));
//    }


}
