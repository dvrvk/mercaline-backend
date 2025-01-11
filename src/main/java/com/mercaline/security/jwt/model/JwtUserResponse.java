package com.mercaline.security.jwt.model;

import com.mercaline.users.dto.ResponseUserSummaryDTO;
import lombok.*;

/**
 * Gets the token.
 *
 * @return the token
 */
@Getter

/**
 * Sets the token.
 *
 * @param token the new token
 */
@Setter

/**
 * Instantiates a new jwt user response.
 */
@NoArgsConstructor
public class JwtUserResponse extends ResponseUserSummaryDTO {

    /** The token. */
    private String token;

    /**
     * To string.
     *
     * @return the java.lang. string
     */
    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(Long id, String username, String email, String tel, String token) {
        super(id, username, email, tel);
        this.token = token;
    }
}
