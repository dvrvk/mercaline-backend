package com.mercaline.security;

import com.mercaline.security.jwt.JwtTokenProvider;
import com.mercaline.security.jwt.model.JwtUserResponse;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.RequestUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class AuthController.
 */
@RestController

/**
 * Instantiates a new auth controller.
 *
 * @param authenticationManager the authentication manager
 * @param jwtTokenProvider the jwt token provider
 */
@RequiredArgsConstructor
public class AuthController {
    
    /** The authentication manager. */
    private final AuthenticationManager authenticationManager;
    
    /** The jwt token provider. */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Login.
     *
     * @param loginRequest the login request
     * @return the jwt user response
     */
    //  public JwtUserResponse login(@Validated @RequestBody LoginRequest loginRequest) { -> sustituyo por RequestUserDTO
    @PostMapping("/auth/login")
    public JwtUserResponse login(@Validated @RequestBody RequestUserDTO loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        UserEntity user = (UserEntity) auth.getPrincipal();

        String jwtToken = jwtTokenProvider.generarToken(auth);

        return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);
    }

    /**
     * Convert user entity and token to jwt user response.
     *
     * @param user the user
     * @param jwtToken the jwt token
     * @return the jwt user response
     */
    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(UserEntity user, String jwtToken) {
        return JwtUserResponse.jwtUserResponseBuilder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tel(user.getTel())
                .token(jwtToken)
                .build();
    }

}
