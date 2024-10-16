package com.mercaline.security.jwt.model;

import com.mercaline.users.dto.ResponseUserSummaryDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends ResponseUserSummaryDTO {

    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(Long id, String username, String email, String tel, String token) {
        super(id, username, email, tel);
        this.token = token;
    }
}
