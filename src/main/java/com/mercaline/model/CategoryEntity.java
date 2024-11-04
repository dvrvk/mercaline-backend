package com.mercaline.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa las categorías disponibles para el producto
 */
@Entity
@Table(name="categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name="name")
    private String name;

}
