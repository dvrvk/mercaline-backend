package com.mercaline.config.utils;

public class AppConstants {
    // Constantes de validacion de usuario
    public static final String USERNAME_NOTBLANK_MSG = "El nombre de usuario es obligatorio.";
    public static final String USERNAME_ERRORSIZE_MSG = "El nombre de usuario debe tener al menos 3 caracteres y máximo 30";
    public static final String USERNAME_REGEXP_MSG = "El nombre de usuario solo puede contener letras y números";
    public static final int USERNAME_MAX_SIZE = 30;
    public static final int USERNAME_MIN_SIZE = 3;
    public static final String USERNAME_REGEXP = "^[a-zA-Z0-9]+$";

    public static final int NAME_MAX_SIZE = 50;
    public static final String NAME_MAX_SIZE_MSG = "El nombre no puede tener más de 50 caracteres";
    public static final String NAME_REGEXP = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$";
    public static final String NAME_REGEXP_MSG = "El nombre solo puede contener letras";

    public static final int LASTNAME_MAX_SIZE = 50;
    public static final String LASTNAME_MAX_SIZE_MSG = "Los apellidos no pueden tener más de 50 caracteres";
    public static final String LASTNAME_REGEXP = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$";
    public static final String LASTNAME_REGEXP_MSG = "Los apellidos solo pueden contener letras y espacios opcionales, pero no al principio ni al final";

    public static final String PASSWORD_NOTBLANK_MSG = "La contraseña es obligatoria.";
    public static final int PASSWORD_MIN_SIZE = 8;
    public static final String PASSWORD_MIN_SIZE_MSG = "La contraseña debe tener al menos 8 caracteres";

    public static final String EMAIL_NOBLANK_MSG = "El correo electrónico es obligatorio";
    public static final String EMAIL_VALID_MSG = "El correo electrónico es obligatorio";

    public static final String TEL_REGEXP = "^$|^((\\+34)?[ -]?[0-9]{9})?$";
    public static final String TEL_REGEXP_MSG = "Introduce un número de teléfono válido en España";
}
