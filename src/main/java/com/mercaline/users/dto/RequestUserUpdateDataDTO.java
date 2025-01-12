package com.mercaline.users.dto;

import static com.mercaline.config.utils.AppConstants.EMAIL_NOBLANK_MSG;
import static com.mercaline.config.utils.AppConstants.EMAIL_VALID_MSG;
import static com.mercaline.config.utils.AppConstants.LASTNAME_MAX_SIZE;
import static com.mercaline.config.utils.AppConstants.LASTNAME_MAX_SIZE_MSG;
import static com.mercaline.config.utils.AppConstants.LASTNAME_REGEXP;
import static com.mercaline.config.utils.AppConstants.LASTNAME_REGEXP_MSG;
import static com.mercaline.config.utils.AppConstants.NAME_MAX_SIZE;
import static com.mercaline.config.utils.AppConstants.NAME_MAX_SIZE_MSG;
import static com.mercaline.config.utils.AppConstants.NAME_REGEXP;
import static com.mercaline.config.utils.AppConstants.NAME_REGEXP_MSG;
import static com.mercaline.config.utils.AppConstants.TEL_REGEXP;
import static com.mercaline.config.utils.AppConstants.TEL_REGEXP_MSG;
import static com.mercaline.config.utils.AppConstants.USERNAME_ERRORSIZE_MSG;
import static com.mercaline.config.utils.AppConstants.USERNAME_MAX_SIZE;
import static com.mercaline.config.utils.AppConstants.USERNAME_MIN_SIZE;
import static com.mercaline.config.utils.AppConstants.USERNAME_NOTBLANK_MSG;
import static com.mercaline.config.utils.AppConstants.USERNAME_REGEXP;
import static com.mercaline.config.utils.AppConstants.USERNAME_REGEXP_MSG;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
 * Instantiates a new request user update data DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new request user update data DTO.
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
public class RequestUserUpdateDataDTO {

	/** The id. */
	private Long id;

	/** The username. */
	@NotBlank(message = USERNAME_NOTBLANK_MSG)
	@Size(min = USERNAME_MIN_SIZE, max = USERNAME_MAX_SIZE, message = USERNAME_ERRORSIZE_MSG)
	@Pattern(regexp = USERNAME_REGEXP, message = USERNAME_REGEXP_MSG)
	private String username;

	/** The name. */
	@Size(max = NAME_MAX_SIZE, message = NAME_MAX_SIZE_MSG)
	@NotBlank
	@Pattern(regexp = NAME_REGEXP, message = NAME_REGEXP_MSG)
	private String name;

	/** The lastname. */
	@NotBlank
	@Size(max = LASTNAME_MAX_SIZE, message = LASTNAME_MAX_SIZE_MSG)
	@Pattern(regexp = LASTNAME_REGEXP, message = LASTNAME_REGEXP_MSG)
	private String lastname;

	/** The email. */
	@NotBlank(message = EMAIL_NOBLANK_MSG)
	@Email(message = EMAIL_VALID_MSG)
	private String email;

	/** The tel. */
	@Pattern(regexp = TEL_REGEXP, message = TEL_REGEXP_MSG)
	private String tel;
}
