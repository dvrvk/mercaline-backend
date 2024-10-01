package com.mercaline.security;

import com.mercaline.security.jwt.JwtTokenProvider;
import com.mercaline.security.jwt.model.JwtUserResponse;
import com.mercaline.security.jwt.model.LoginRequest;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.GetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/auth/login")
    public JwtUserResponse login(@Validated @RequestBody LoginRequest loginRequest) {

        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        UserEntity user = (UserEntity) auth.getPrincipal();

        String jwtToken = jwtTokenProvider.generarToken(auth);

        //TODO cambiar a un DTO para no devolver todo
        return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);
    }

    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(UserEntity user, String jwtToken) {
        return JwtUserResponse.jwtUserResponseBuilder()
                .username(user.getUsername())
                .email(user.getEmail())
                .tel(user.getTel())
                .token(jwtToken)
                .build();
    }

}
