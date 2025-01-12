package com.mercaline.config.utils;

import java.util.List;

/**
 * The Class AppConstants.
 */
public class AppConstants {
	
	/** The Constant USERNAME_NOTBLANK_MSG. */
	// Constantes de validacion de usuario
	public static final String USERNAME_NOTBLANK_MSG = "El nombre de usuario es obligatorio.";
	
	/** The Constant USERNAME_ERRORSIZE_MSG. */
	public static final String USERNAME_ERRORSIZE_MSG = "El nombre de usuario debe tener al menos 3 caracteres y máximo 30";
	
	/** The Constant USERNAME_REGEXP_MSG. */
	public static final String USERNAME_REGEXP_MSG = "El nombre de usuario solo puede contener letras y números";
	
	/** The Constant USERNAME_MAX_SIZE. */
	public static final int USERNAME_MAX_SIZE = 30;
	
	/** The Constant USERNAME_MIN_SIZE. */
	public static final int USERNAME_MIN_SIZE = 3;
	
	/** The Constant USERNAME_REGEXP. */
	public static final String USERNAME_REGEXP = "^[a-zA-Z0-9]+$";

	/** The Constant NAME_MAX_SIZE. */
	public static final int NAME_MAX_SIZE = 50;
	
	/** The Constant NAME_MAX_SIZE_MSG. */
	public static final String NAME_MAX_SIZE_MSG = "El nombre no puede tener más de 50 caracteres";
	
	/** The Constant NAME_REGEXP. */
	public static final String NAME_REGEXP = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$";
	
	/** The Constant NAME_REGEXP_MSG. */
	public static final String NAME_REGEXP_MSG = "El nombre solo puede contener letras";

	/** The Constant LASTNAME_MAX_SIZE. */
	public static final int LASTNAME_MAX_SIZE = 50;
	
	/** The Constant LASTNAME_MAX_SIZE_MSG. */
	public static final String LASTNAME_MAX_SIZE_MSG = "Los apellidos no pueden tener más de 50 caracteres";
	
	/** The Constant LASTNAME_REGEXP. */
	public static final String LASTNAME_REGEXP = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$";
	
	/** The Constant LASTNAME_REGEXP_MSG. */
	public static final String LASTNAME_REGEXP_MSG = "Los apellidos solo pueden contener letras y espacios opcionales, pero no al principio ni al final";

	/** The Constant PASSWORD_NOTBLANK_MSG. */
	public static final String PASSWORD_NOTBLANK_MSG = "La contraseña es obligatoria.";
	
	/** The Constant PASSWORD_MIN_SIZE. */
	public static final int PASSWORD_MIN_SIZE = 8;
	
	/** The Constant PASSWORD_MIN_SIZE_MSG. */
	public static final String PASSWORD_MIN_SIZE_MSG = "La contraseña debe tener al menos 8 caracteres";

	/** The Constant EMAIL_NOBLANK_MSG. */
	public static final String EMAIL_NOBLANK_MSG = "El correo electrónico es obligatorio";
	
	/** The Constant EMAIL_VALID_MSG. */
	public static final String EMAIL_VALID_MSG = "El correo electrónico es obligatorio";

	/** The Constant TEL_REGEXP. */
	public static final String TEL_REGEXP = "^$|^((\\+34)?[ -]?[0-9]{9})?$";
	
	/** The Constant TEL_REGEXP_MSG. */
	public static final String TEL_REGEXP_MSG = "Introduce un número de teléfono válido en España";

	/** The Constant PATH_IMG. */
	public static final String PATH_IMG = "images/";

	/** The Constant ID_NOTNULL_MSG. */
	// Constantes validación producto
	public static final String ID_NOTNULL_MSG = "El id es obligatorio";
	
	/** The Constant ID_MIN. */
	public static final int ID_MIN = 0;
	
	/** The Constant ID_MIN_MSG. */
	public static final String ID_MIN_MSG = "El id tiene que ser mayor que 0";

	/** The Constant NAMEP_NOTBLANK_MSG. */
	public static final String NAMEP_NOTBLANK_MSG = "El nombre es obligatorio";
	
	/** The Constant NAMEP_MIN_SIZE. */
	public static final int NAMEP_MIN_SIZE = 2;
	
	/** The Constant NAMEP_MAX_SIZE. */
	public static final int NAMEP_MAX_SIZE = 100;
	
	/** The Constant NAMEP_SIZE_MSG. */
	public static final String NAMEP_SIZE_MSG = "El tamaño debe estar entre 2 y 100 caracteres";

	/** The Constant DESCRIPTIONP_NOTBLANK_MSG. */
	public static final String DESCRIPTIONP_NOTBLANK_MSG = "La descripción es obligatoria";
	
	/** The Constant DESCRIPTIONP_MIN_SIZE. */
	public static final int DESCRIPTIONP_MIN_SIZE = 3;
	
	/** The Constant DESCRIPTIONP_MAX_SIZE. */
	public static final int DESCRIPTIONP_MAX_SIZE = 1000;
	
	/** The Constant DESCRIPTIONP_SIZE_MSG. */
	public static final String DESCRIPTIONP_SIZE_MSG = "El tamaño debe estar entre 2 y 100 caracteres";

	/** The Constant PRICE_NOT_NULL_MSG. */
	public static final String PRICE_NOT_NULL_MSG = "El precio es obligatorio";
	
	/** The Constant PRICE_DIGITS_MSG. */
	public static final String PRICE_DIGITS_MSG = "El precio debe ser un número válido con hasta 10 dígitos enteros y 2 decimales";
	
	/** The Constant PRICE_MIN_MSG. */
	public static final String PRICE_MIN_MSG = "El precio debe ser mayor que 0";
	
	/** The Constant PRICE_DIGITS_INTEGER. */
	public static final int PRICE_DIGITS_INTEGER = 20;
	
	/** The Constant PRICE_DIGITS_FRACTION. */
	public static final int PRICE_DIGITS_FRACTION = 2;
	
	/** The Constant PRICE_DECIMAL_MIN. */
	public static final String PRICE_DECIMAL_MIN = "0.0";
	
	/** The Constant PRICE_DECIMAL_MIN_INCLUSIVE. */
	public static final boolean PRICE_DECIMAL_MIN_INCLUSIVE = false;

	/** The Constant STATUSP_MSG. */
	public static final String STATUSP_MSG = "El estado del producto es obligatorio";
	
	/** The Constant STATUSP_MIN. */
	public static final int STATUSP_MIN = 1;
	
	/** The Constant STATUSP_MIN_MSG. */
	public static final String STATUSP_MIN_MSG = "El estado debe ser un número mayor que 0";
	
	/** The Constant STATUSP_DIGITS_INTEGER. */
	public static final int STATUSP_DIGITS_INTEGER = 19;
	
	/** The Constant STATUSP_DIGITS_FRACTION. */
	public static final int STATUSP_DIGITS_FRACTION = 0;
	
	/** The Constant STATUS_DIGITS_MSG. */
	public static final String STATUS_DIGITS_MSG = "El estado debe ser un número entero sin decimales";

	/** The Constant CATEGORY_NOT_NULL. */
	public static final String CATEGORY_NOT_NULL = "La categoría es obligatoria";
	
	/** The Constant CATEGORY_DIGITS_INTEGER. */
	public static final int CATEGORY_DIGITS_INTEGER = 3;
	
	/** The Constant CATEGORY_DIGITS_FRACTION. */
	public static final int CATEGORY_DIGITS_FRACTION = 0;
	
	/** The Constant CATEGORY_DECIMAL_MIN. */
	public static final String CATEGORY_DECIMAL_MIN = "0.0";
	
	/** The Constant CATEGORY_DECIMAL_MIN_INCLUSIVE. */
	public static final boolean CATEGORY_DECIMAL_MIN_INCLUSIVE = false;

	/** The Constant PROD_IMG_SIZE_MIN. */
	public static final int PROD_IMG_SIZE_MIN = 1;
	
	/** The Constant PROD_IMG_SIZE_MAX. */
	public static final int PROD_IMG_SIZE_MAX = 5;
	
	/** The Constant PROD_IMG_SIZE_MSG. */
	public static final String PROD_IMG_SIZE_MSG = "Debes subir entre 1 y 5 imágenes";

	/** The Constant CP_NOT_NULL. */
	public static final String CP_NOT_NULL = "El código postal es obligatorio";

	/** The Constant UPDATE_IMAGES_OPTIONS. */
	// Options images update
	public static final List<String> UPDATE_IMAGES_OPTIONS = List.of("no-modificar", "sustituir", "agregar");

	/** The Constant ID_FAV_NOT_NULL_MSG. */
	// Constantes validación favoritos
	public static final String ID_FAV_NOT_NULL_MSG = "El id del producto no puede ser nulo";
	
	/** The Constant MIN_ID_FAV. */
	public static final int MIN_ID_FAV = 0;
	
	/** The Constant MIN_ID_FAV_MSG. */
	public static final String MIN_ID_FAV_MSG = "El id tiene que ser como mínimo 1";
}
