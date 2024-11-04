package com.mercaline.users.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.mercaline.config.utils.AppConstants.*;
import static com.mercaline.config.utils.AppConstants.USERNAME_REGEXP_MSG;

/**
 * Entidad que representa a un usuario de la aplicacion.
 */
@Entity
@Table(name = "users")

/**
 * Sets the tel.
 *
 * @param tel the new tel
 */
@Setter

/**
 * Gets the tel.
 *
 * @return the tel
 */
@Getter

/**
 * Instantiates a new user entity.
 *
 * @param id the id
 * @param username the username
 * @param name the name
 * @param lastname the lastname
 * @param password the password
 * @param email the email
 * @param tel the tel
 */
@AllArgsConstructor

/**
 * Instantiates a new user entity.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class UserEntity implements UserDetails {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The username. */
    @Column(unique = true, nullable = false, name = "username")
    @NotBlank(message = USERNAME_NOTBLANK_MSG)
    @Size(min = USERNAME_MIN_SIZE, max=USERNAME_MAX_SIZE, message = USERNAME_ERRORSIZE_MSG)
    @Pattern(regexp = USERNAME_REGEXP, message = USERNAME_REGEXP_MSG)
    private String username;

    /** The name. */
    @Column(nullable = false, name = "name")
    @Size(max = NAME_MAX_SIZE, message = NAME_MAX_SIZE_MSG)
    @NotBlank
    @Pattern(regexp = NAME_REGEXP, message = NAME_REGEXP_MSG)
    private String name;

    /** The lastname. */
    @Column(nullable = false, name = "lastname")
    @NotBlank
    @Size(max = LASTNAME_MAX_SIZE, message = LASTNAME_MAX_SIZE_MSG)
    @Pattern(regexp = LASTNAME_REGEXP, message = LASTNAME_REGEXP_MSG)
    private String lastname;

    /** The password. */
    @Column(nullable = false, name = "password")
    @NotBlank(message = PASSWORD_NOTBLANK_MSG)
    @Size(min = PASSWORD_MIN_SIZE, message = PASSWORD_MIN_SIZE_MSG)
    private String password;

    /** The email. */
    @Column(nullable = false, unique = true, name = "email")
    @NotBlank(message = EMAIL_NOBLANK_MSG)
    @Email(message = EMAIL_VALID_MSG)
    private String email;

    /** The tel. */
    @Column(name = "tel")
    @Pattern(regexp = TEL_REGEXP, message = TEL_REGEXP_MSG)
    private String tel;

    /**
     * Gets the authorities.
     *
     * @return the authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
