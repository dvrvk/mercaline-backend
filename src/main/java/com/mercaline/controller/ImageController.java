package com.mercaline.controller;

import com.mercaline.dto.ApiResponse;
import com.mercaline.error.exceptions.ImageNotFound;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.ProductEntity;
import com.mercaline.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

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


    @GetMapping("/{id}")
    public ResponseEntity<?> findAll(@PathVariable Long id) {

        ProductEntity product = this.productService.findById(id)
                .orElseThrow(ProductoNotFoundException::new);

        String[] imageResources = product.getUrlImage().split(";");

        // Evita que las imagenes de prueba fallen
        if(!isURL(imageResources[0])) {
            List<String> base64Images = new ArrayList<>();

            for(String imageResource : imageResources) {
                try {
                    Path path = Paths.get(imageResource);
                    byte[] imageBytes = Files.readAllBytes(path);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    base64Images.add(base64Image);
                } catch (IOException e) {
                    String imageName = Paths.get(imageResources[0]).getFileName().toString();
                    throw new ImageNotFound(imageName);
                }
            }
            // Convierte la lista de imágenes Base64 en una cadena separada por comas
            String imagesString = String.join(",", base64Images);
            ApiResponse response = new ApiResponse(HttpStatus.OK, imagesString);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ApiResponse response = new ApiResponse(HttpStatus.OK, imageResources[0]);
        return ResponseEntity.ok(response);

    }


    private boolean isURL(String path) {
        String urlPattern = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
        return Pattern.matches(urlPattern, path);
    }

}
