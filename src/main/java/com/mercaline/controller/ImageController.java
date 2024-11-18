package com.mercaline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercaline.error.exceptions.ImageNotFound;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.ProductEntity;
import com.mercaline.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

	private final ProductService productService;

	@GetMapping("/main/{id}")
	public ResponseEntity<?> findMain(@PathVariable Long id) {

		ProductEntity product = this.productService.findById(id).orElseThrow(ProductoNotFoundException::new);
		String[] imagesUrls = product.getUrlImage().split(";");
		// Para evitar que la imagenes de prueba fallen
		if (!isUrl(imagesUrls[0])) {
			try {
				Path path = Paths.get(imagesUrls[0]);
				byte[] imageBytes = Files.readAllBytes(path);

				// Convertir los bytes en un recurso
				ByteArrayResource resource = new ByteArrayResource(imageBytes);

				// Configurar los encabezados de la respuesta
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.IMAGE_JPEG);
				headers.setContentLength(imageBytes.length);

				return new ResponseEntity<>(resource, headers, HttpStatus.OK);

			} catch (IOException e) {
				String imageName = Paths.get(imagesUrls[0]).getFileName().toString();
				throw new ImageNotFound(imageName);
			}
		} else {
			return ResponseEntity.ok(imagesUrls[0]);
		}

	}

	private boolean isUrl(String path) {
		String urlPattern = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
		return Pattern.matches(urlPattern, path);
	}
}
