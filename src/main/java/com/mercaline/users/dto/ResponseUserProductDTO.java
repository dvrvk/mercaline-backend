package com.mercaline.users.dto;

import lombok.*;

/**
 * Gets the username.
 *
 * @return the username
 */
@Getter

/**
 * Sets the username.
 *
 * @param username the new username
 */
@Setter

/**
 * Instantiates a new response user product DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new response user product DTO.
 *
 * @param id the id
 * @param username the username
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ResponseUserProductDTO {
    
    /** The id. */
    private Long id;
    
    /** The username. */
    private String username;
}
