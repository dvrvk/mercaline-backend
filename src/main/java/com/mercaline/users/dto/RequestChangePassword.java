package com.mercaline.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.mercaline.config.utils.AppConstants.*;
import static com.mercaline.config.utils.AppConstants.PASSWORD_MIN_SIZE_MSG;

/**
 * Gets the new password.
 *
 * @return the new password
 */
@Getter

/**
 * Sets the new password.
 *
 * @param newPassword the new new password
 */
@Setter

/**
 * Instantiates a new request change password.
 */
@NoArgsConstructor

/**
 * Instantiates a new request change password.
 *
 * @param password the password
 * @param newPassword the new password
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class RequestChangePassword {
    
    /** The password. */
    @NotBlank(message = PASSWORD_NOTBLANK_MSG)
    @Size(min = PASSWORD_MIN_SIZE, message = PASSWORD_MIN_SIZE_MSG)
    String password;
    
    /** The new password. */
    @NotBlank(message = PASSWORD_NOTBLANK_MSG)
    @Size(min = PASSWORD_MIN_SIZE, message = PASSWORD_MIN_SIZE_MSG)
    String newPassword;
}
