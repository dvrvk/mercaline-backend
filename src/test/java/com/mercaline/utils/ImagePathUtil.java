/**
 * 
 */
package com.mercaline.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Class ImagePathUtil.
 */
public class ImagePathUtil {

	/**
	 * Gets the image bytes.
	 *
	 * @param imageName the image name
	 * @return the image bytes
	 * @throws IOException        Signals that an I/O exception has occurred.
	 * @throws URISyntaxException the URI syntax exception
	 */
	public static Path getImageBytes(String imageName) throws IOException, URISyntaxException {
		// Obtén la ruta base del proyecto
		String projectDir = System.getProperty("user.dir");
		// Construye la ruta completa a partir de la ruta base y el nombre de la imagen
		return Paths.get(projectDir, "src", "test", "resources", imageName);
	}
}
