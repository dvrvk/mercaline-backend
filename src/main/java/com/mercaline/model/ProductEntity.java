package com.mercaline.model;

import com.mercaline.users.Model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un producto de un usuario
 */
@Entity
@Table(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El tamaño debe estar entre 2 y  100 caracteres")
    private String name;

    @Column(length = 1000, nullable = false, name = "description")
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 3, max = 1000, message = "El tamaño debe estar entre 3 y  1000 caracteres")
    private String description;

    @Column(nullable = false, name = "price")
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category", nullable = false)
    @NotNull(message = "La categoría es obligatoria")
    private CategoryEntity category;

    @Column(name= "create_date", nullable = false, updatable = false)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @NotNull(message = "El vendedor es obligatorio")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status", nullable = false)
    @NotNull(message = "El estado es obligatorio")
    private StatusEntity status;

    @Column(name = "url_image")
    @NotNull(message = "Es obligatorio subir al menos una imagen")
    private String urlImage;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }
}
