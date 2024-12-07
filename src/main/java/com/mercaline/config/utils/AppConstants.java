package com.mercaline.config.utils;

import java.util.List;

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

    public static final String PATH_IMG = "images/";

    // Constantes validación producto
    public static final String ID_NOTNULL_MSG = "El id es obligatorio";
    public static final int ID_MIN = 0;
    public static final String ID_MIN_MSG = "El id tiene que ser mayor que 0";

    public static final String NAMEP_NOTBLANK_MSG = "El nombre es obligatorio";
    public static final int NAMEP_MIN_SIZE = 2;
    public static final int NAMEP_MAX_SIZE = 100;
    public static final String NAMEP_SIZE_MSG = "El tamaño debe estar entre 2 y 100 caracteres";

    public static final String DESCRIPTIONP_NOTBLANK_MSG = "La descripción es obligatoria";
    public static final int DESCRIPTIONP_MIN_SIZE = 3;
    public static final int DESCRIPTIONP_MAX_SIZE = 1000;
    public static final String DESCRIPTIONP_SIZE_MSG = "El tamaño debe estar entre 2 y 100 caracteres";

    public static final String PRICE_NOT_NULL_MSG = "El precio es obligatorio";
    public static final String PRICE_DIGITS_MSG = "El precio debe ser un número válido con hasta 10 dígitos enteros y 2 decimales";
    public static final String PRICE_MIN_MSG = "El precio debe ser mayor que 0";
    public static final int PRICE_DIGITS_INTEGER = 20;
    public static final int PRICE_DIGITS_FRACTION = 2;
    public static final String PRICE_DECIMAL_MIN = "0.0";
    public static final boolean PRICE_DECIMAL_MIN_INCLUSIVE = false;

    public static final String STATUSP_MSG = "El estado del producto es obligatorio";
    public static final int STATUSP_MIN = 1;
    public static final String STATUSP_MIN_MSG = "El estado debe ser un número mayor que 0";
    public static final int STATUSP_DIGITS_INTEGER = 19;
    public static final int STATUSP_DIGITS_FRACTION = 0;
    public static final String STATUS_DIGITS_MSG = "El estado debe ser un número entero sin decimales";

    public static final String CATEGORY_NOT_NULL = "La categoría es obligatoria";
    public static final int CATEGORY_DIGITS_INTEGER = 3;
    public static final int CATEGORY_DIGITS_FRACTION = 0;
    public static final String CATEGORY_DECIMAL_MIN = "0.0";
    public static final boolean CATEGORY_DECIMAL_MIN_INCLUSIVE = false;

    public static final int PROD_IMG_SIZE_MIN = 1;
    public static final int PROD_IMG_SIZE_MAX = 5;
    public static final String PROD_IMG_SIZE_MSG = "Debes subir entre 1 y 5 imágenes";

    // Options images update
    public static final List<String> UPDATE_IMAGES_OPTIONS = List.of("no-modificar", "sustituir", "agregar");

    // Constantes validación favoritos
    public static final String ID_FAV_NOT_NULL_MSG = "El id del producto no puede ser nulo";
    public static final int MIN_ID_FAV = 0;
    public static final String MIN_ID_FAV_MSG = "El id tiene que ser como mínimo 1";
}
