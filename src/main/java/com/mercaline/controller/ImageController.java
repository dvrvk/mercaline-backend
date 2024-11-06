package com.mercaline.controller;

import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.ProductEntity;
import com.mercaline.service.ProductService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
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

        // Para evitar que la imagenes de prueba fallen
        if(!isUrl(product.getUrlImage())) {
            try {
                Path path = Paths.get(product.getUrlImage());
                byte[] imageBytes = Files.readAllBytes(path);

                // Convertir los bytes en un recurso
                ByteArrayResource resource = new ByteArrayResource(imageBytes);

                // Configurar los encabezados de la respuesta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                headers.setContentLength(imageBytes.length);

                return new ResponseEntity<>(resource, headers, HttpStatus.OK);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ResponseEntity.ok(product.getUrlImage());
        }

    }


    private boolean isUrl(String path) {
        String urlPattern = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
        return Pattern.matches(urlPattern, path);
    }
}
