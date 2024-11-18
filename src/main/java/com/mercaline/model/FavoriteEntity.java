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
 * Entidad que representa los productos favoritos y en que lista se alojan
 */
@Entity
@Table(name = "favourites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="list_fav_id", nullable = false)
    private ListFavoriteEntity favoriteList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable = false)
    private ProductEntity product;

}
