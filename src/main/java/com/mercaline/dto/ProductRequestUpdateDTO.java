package com.mercaline.dto;

import com.mercaline.users.Model.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

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
 * Instantiates a new product request update DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new product request update DTO.
 *
 * @param id the id
 * @param name the name
 * @param description the description
 * @param price the price
 * @param status the status
 * @param category the category
 * @param imageOption the image option
 * @param images the images
 * @param user the user
 * @param cp the cp
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ProductRequestUpdateDTO {
    
    /** The id. */
    private Long id;
    
    /** The name. */
    private String name;
    
    /** The description. */
    private String description;
    
    /** The price. */
    private BigDecimal price;
    
    /** The status. */
    private Long status;
    
    /** The category. */
    private Long category;
    
    /** The image option. */
    private String imageOption;
    
    /** The images. */
    private MultipartFile[] images;
    
    /** The user. */
    private UserEntity user;
    
    /** The cp. */
    private String cp;
}
