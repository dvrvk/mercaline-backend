package com.mercaline.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa los estados disponibles para el producto
 */
@Entity
@Table(name="status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name="name")
    private String name;
}
