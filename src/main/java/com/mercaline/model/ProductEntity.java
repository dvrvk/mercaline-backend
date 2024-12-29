package com.mercaline.model;

import com.mercaline.users.Model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.mercaline.config.utils.AppConstants.*;
import static com.mercaline.config.utils.AppConstants.NAMEP_SIZE_MSG;

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
    @NotBlank(message = NAMEP_NOTBLANK_MSG)
    @Size(min = NAMEP_MIN_SIZE, max = NAMEP_MAX_SIZE, message = NAMEP_SIZE_MSG)
    private String name;

    @Column(length = 1000, nullable = false, name = "description")
    @NotBlank(message = DESCRIPTIONP_NOTBLANK_MSG)
    @Size(min = DESCRIPTIONP_MIN_SIZE, max = DESCRIPTIONP_MAX_SIZE, message = DESCRIPTIONP_SIZE_MSG)
    private String description;

    @Column(nullable = false, name = "price")
    @NotNull(message = PRICE_NOT_NULL_MSG)
    @Digits(integer = PRICE_DIGITS_INTEGER, fraction = PRICE_DIGITS_FRACTION, message = PRICE_DIGITS_MSG)
    @DecimalMin(value = PRICE_DECIMAL_MIN, inclusive = PRICE_DECIMAL_MIN_INCLUSIVE, message = PRICE_MIN_MSG)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category", nullable = false)
    @NotNull(message = CATEGORY_NOT_NULL)
    private CategoryEntity category;

    @Column(name= "create_date", nullable = false, updatable = false)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime createDate;

    @Column(name="sold")
    private boolean sold = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @NotNull(message = "El vendedor es obligatorio")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status", nullable = false)
    @NotNull(message = STATUSP_MSG)
    private StatusEntity status;

    @Column(name = "url_image")
    @NotNull(message = "Es obligatorio subir al menos una imagen")
    private String urlImage;

    @Column(name = "cp")
    private String cp;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }
}
