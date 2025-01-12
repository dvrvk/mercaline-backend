package com.mercaline.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercaline.users.Model.UserEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Entidad que representa las listas de los usuarios.
 */
@Entity
@Table(name = "list_fav")

/**
 * Gets the user.
 *
 * @return the user
 */
@Getter

/**
 * Sets the user.
 *
 * @param user the new user
 */
@Setter

/**
 * Instantiates a new list favorite entity.
 *
 * @param id the id
 * @param name the name
 * @param user the user
 */
@AllArgsConstructor

/**
 * Instantiates a new list favorite entity.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ListFavoriteEntity {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The name. */
	@Column(name = "name", nullable = false)
	private String name;

    /** The user. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    private UserEntity user;

}
