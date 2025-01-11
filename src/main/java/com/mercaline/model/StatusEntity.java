package com.mercaline.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa los estados disponibles para el producto.
 */
@Entity
@Table(name="status")

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
 * Instantiates a new status entity.
 *
 * @param id the id
 * @param name the name
 */
@AllArgsConstructor

/**
 * Instantiates a new status entity.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class StatusEntity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name. */
    @Column(nullable = false, name="name")
    private String name;
}
