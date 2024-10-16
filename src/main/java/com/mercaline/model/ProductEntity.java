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
@Table(name="productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El tamaño debe estar entre 2 y  100 caracteres")
    private String nombre;

    @Column(length = 1000, nullable = false)
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 3, max = 1000, message = "El tamaño debe estar entre 3 y  1000 caracteres")
    private String descripcion;

    @Column(nullable = false)
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal precio;

    @Column(nullable = false)
    @NotNull(message = "La categoría es obligatoria")
    private String categoria;

    @Column(name= "fecha_creacion", nullable = false, updatable = false)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name="vendedor_id", nullable = false)
    @NotNull(message = "El vendedor es obligatorio")
    private UserEntity usuario;

    @Column(nullable = false)
    @NotNull(message = "El estado es obligatorio")
    private String estado;

    @Column(name = "imagen_url")
    @URL(message = "La URL de la imagen debe ser válida")
    private String imagenUrl;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
