package com.mercaline.dto;

import java.math.BigDecimal;

import com.mercaline.users.dto.ResponseUserCompleteDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Gets the vendedor.
 *
 * @return the vendedor
 */
@Getter

/**
 * Sets the vendedor.
 *
 * @param vendedor the new vendedor
 */
@Setter

/**
 * Instantiates a new product response DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new product response DTO.
 *
 * @param id the id
 * @param name the name
 * @param description the description
 * @param price the price
 * @param status the status
 * @param statusId the id status
 * @param imageURL the image url
 * @param caetgory the category
 * @param id_category the id category
 * @param seller the seller
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ProductResponseDTO {

    /** The id. */
    private Long id;

     /** The name. */
    private String name;
  
    /** The description. */
    private String description;
    
    /** The price. */
    private BigDecimal price;
    
    /** The id status. */
    private Long statusId;
    
    /** The status. */
    private String status;
    
    /** The imagen url. */
    private String imageUrl;
    
    /** The category. */
    private String category;

    /** The id category. */
    private Long id_category;

   /** The seller. */
    private ResponseUserCompleteDTO seller;

    /** Postal code **/
    private String cp;

    /** The sold. */
    private boolean sold;
}
