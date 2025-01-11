package com.mercaline.dto;

import com.mercaline.users.dto.ResponseUserProductDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Checks if is sold.
 *
 * @return true, if is sold
 */
@Getter

/**
 * Sets the sold.
 *
 * @param sold the new sold
 */
@Setter

/**
 * Instantiates a new product response summary DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new product response summary DTO.
 *
 * @param id the id
 * @param name the name
 * @param description the description
 * @param status the status
 * @param imageUrl the image url
 * @param price the price
 * @param category the category
 * @param createDate the create date
 * @param seller the seller
 * @param cp the cp
 * @param sold the sold
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ProductResponseSummaryDTO {

    /** The id. */
    private Long id;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    /** The status. */
    private String status;

    /** The image url. */
    private String imageUrl;

    /** The price. */
    private BigDecimal price;

    /** The category. */
    private String category;

    /** The create date. */
    private LocalDateTime createDate;

    /** The seller. */
    private ResponseUserProductDTO seller;

    /** The cp. */
    private String cp;

    /** The sold. */
    private boolean sold;
}
