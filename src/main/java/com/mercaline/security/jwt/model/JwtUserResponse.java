package com.mercaline.security.jwt.model;

import com.mercaline.users.dto.GetUserDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends GetUserDto {

    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String username, String email, String tel, String token) {
        super(username, email, tel);
        this.token = token;
    }
}
