package com.mercaline.users.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa a un usuario de la aplicacion
 */
@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "username")
    @NotBlank(message = "El nombre de usuario es obligatorio.")
    @Size(min = 3, max=30, message = "El nombre de usuario debe tener al menos 3 caracteres y máximo 30")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]+$", message = "El nombre de usuario solo puede contener letras y números")
    private String username;

    @Column(nullable = false, name = "name")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @NotBlank
    @Pattern(regexp = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El nombre solo puede contener letras")
    private String name;

    @Column(nullable = false, name = "lastname")
    @NotBlank
    @Size(max = 50, message = "Los apellidos no pueden tener más de 50 caracteres")
    @Pattern(regexp = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$", message = "Los apellidos solo pueden contener letras y espacios opcionales, pero no al principio ni al final")
    private String lastname;

    @Column(nullable = false, name = "password")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @Column(nullable = false, unique = true, name = "email")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Introduce un correo electrónico válido")
    private String email;

    @Column(name = "tel")
    @Pattern(regexp = "^$|^((\\+34)?[ -]?[0-9]{9})?$", message = "Introduce un número de teléfono válido en España")
    private String tel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    
}

