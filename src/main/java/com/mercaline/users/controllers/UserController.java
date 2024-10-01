package com.mercaline.users.controllers;

import com.mercaline.users.dto.GetUserDto;
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


}
