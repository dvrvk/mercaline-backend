package com.mercaline.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static com.mercaline.config.utils.AppConstants.*;

/**
 * Gets the cp.
 *
 * @return the cp
 */
@Getter

/**
 * Sets the cp.
 *
 * @param cp the new cp
 */
@Setter

/**
 * Instantiates a new product request DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new product request DTO.
 *
 * @param name the name
 * @param description the description
 * @param price the price
 * @param status the status
 * @param urlImage the url image
 * @param category the category
 * @param cp the cp
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ProductRequestDTO {

    /** The name. */
    @NotBlank(message = NAMEP_NOTBLANK_MSG)
    @Size(min = NAMEP_MIN_SIZE, max = NAMEP_MAX_SIZE, message = NAMEP_SIZE_MSG)
    private String name;

    /** The description. */
    @NotBlank(message = DESCRIPTIONP_NOTBLANK_MSG)
    @Size(min = DESCRIPTIONP_MIN_SIZE, max = DESCRIPTIONP_MAX_SIZE, message = DESCRIPTIONP_SIZE_MSG)
    private String description;

    /** The price. */
    @NotNull(message = PRICE_NOT_NULL_MSG)
    @Digits(integer = PRICE_DIGITS_INTEGER, fraction = PRICE_DIGITS_FRACTION, message = PRICE_DIGITS_MSG)
    @DecimalMin(value = PRICE_DECIMAL_MIN, inclusive = PRICE_DECIMAL_MIN_INCLUSIVE, message = PRICE_MIN_MSG)
    private BigDecimal price;

    /** The status. */
    @NotNull(message = STATUSP_MSG)
    @Min(value= STATUSP_MIN, message = STATUSP_MIN_MSG)
    @Digits(integer = STATUSP_DIGITS_INTEGER, fraction = STATUSP_DIGITS_FRACTION, message = STATUS_DIGITS_MSG)
    private Long status;


    /** The url image. */
    @Size(min = PROD_IMG_SIZE_MIN, max = PROD_IMG_SIZE_MAX, message = PROD_IMG_SIZE_MSG)
    private MultipartFile[] urlImage;

    /** The category. */
    @NotNull(message = CATEGORY_NOT_NULL)
    @Digits(integer = CATEGORY_DIGITS_INTEGER, fraction = CATEGORY_DIGITS_FRACTION)
    @DecimalMin(value = CATEGORY_DECIMAL_MIN, inclusive = CATEGORY_DECIMAL_MIN_INCLUSIVE)
    private Long category;

    /** The cp. */
    @NotNull(message = CP_NOT_NULL)
    private String cp;
}
