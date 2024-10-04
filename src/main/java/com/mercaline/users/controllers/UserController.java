package com.mercaline.users.controllers;

import com.mercaline.dto.GetProductDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.service.ProductService;
import com.mercaline.users.dto.GetUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public GetUserDto createUser(@RequestBody UserEntity user) {
        return convertToGetUserDto(this.userEntityService.newUser(user));
    }

    @GetMapping("/me")
    public GetUserDto me(@AuthenticationPrincipal UserEntity user) {
        return convertToGetUserDto(user);
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

    private GetUserDto convertToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .tel(user.getTel())
                .build();
    }


}
