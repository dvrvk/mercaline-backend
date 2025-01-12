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
 * Instantiates a new response user complete DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new response user complete DTO.
 *
 * @param id the id
 * @param username the username
 * @param name the name
 * @param lastname the lastname
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
public class ResponseUserCompleteDTO {
    
    /** The id. */
    private Long id;

    /** The username. */
    private String username;

    /** The name. */
    private String name;

    /** The lastname. */
    private String lastname;

    /** The email. */
    private String email;

    /** The tel. */
    private String tel;
}
