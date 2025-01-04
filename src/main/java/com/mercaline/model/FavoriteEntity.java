package com.mercaline.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa los productos favoritos y en que lista se alojan.
 */
@Entity
@Table(name = "favourites")

/**
 * Gets the product.
 *
 * @return the product
 */
@Getter

/**
 * Sets the product.
 *
 * @param product the new product
 */
@Setter

/**
 * Instantiates a new favorite entity.
 *
 * @param id the id
 * @param favoriteList the favorite list
 * @param product the product
 */
@AllArgsConstructor

/**
 * Instantiates a new favorite entity.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class FavoriteEntity {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    /** The favorite list. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="list_fav_id", nullable = false)
    private ListFavoriteEntity favoriteList;

    /** The product. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable = false)
    private ProductEntity product;

}
