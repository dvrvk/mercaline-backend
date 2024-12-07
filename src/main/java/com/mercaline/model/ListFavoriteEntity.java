package com.mercaline.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercaline.users.Model.UserEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Entidad que representa las listas de los usuarios
 */
@Entity
@Table(name = "list_fav")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ListFavoriteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    private UserEntity user;

}
