package com.mercaline.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa las categorías disponibles para el producto.
 */
@Entity
@Table(name="categories")

/**
 * Gets the name.
 *
 * @return the name
 */
@Getter

/**
 * Sets the name.
 *
 * @param name the new name
 */
@Setter

/**
 * Instantiates a new category entity.
 *
 * @param id the id
 * @param name the name
 */
@AllArgsConstructor

/**
 * Instantiates a new category entity.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class CategoryEntity {
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name. */
    @Column(nullable = false, name="name")
    private String name;

}
