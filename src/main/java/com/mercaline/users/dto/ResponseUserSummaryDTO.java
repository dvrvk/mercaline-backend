package com.mercaline.users.dto;

import lombok.*;

/**
 * Gets the tel.
 *
 * @return the tel
 */
@Getter

/**
 * Sets the tel.
 *
 * @param tel the new tel
 */
@Setter

/**
 * Instantiates a new response user summary DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new response user summary DTO.
 *
 * @param id the id
 * @param username the username
 * @param email the email
 * @param tel the tel
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ResponseUserSummaryDTO {
    
    /** The id. */
    private Long id;
    
    /** The username. */
    private String username;
    
    /** The email. */
    private String email;
    
    /** The tel. */
    private String tel;
}
