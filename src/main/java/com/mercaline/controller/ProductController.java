package com.mercaline.controller;

import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.dto.ProductRequestDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.error.exceptions.ImageStorageException;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.CategoryEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.model.StatusEntity;
import com.mercaline.service.CategoryService;
import com.mercaline.service.ProductService;
import com.mercaline.service.StatusService;
import com.mercaline.users.Model.UserEntity;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.mercaline.config.utils.AppConstants.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

	private final ProductService productService;
	private final ProductoDTOConverter productoDTOConverter;
	private final CategoryService categoryService;
	private final StatusService statusService;

	// Todos los productos
	@GetMapping
	public ResponseEntity<Page<ProductResponseSummaryDTO>> findAll(Pageable pageable) {
		Page<ProductResponseSummaryDTO> products = (this.productService.findAll(pageable))
				.map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
		return ResponseEntity.ok().body(products);
	}

	// Producto por id
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok()
				.body((this.productService.findById(id)).map(this.productoDTOConverter::convertToProductDTO)
						.orElseThrow(() -> new ProductoNotFoundException(id)));
	}
	// Productos del usuario (mis productos)
	@GetMapping("/user/{userId}") 
    public ResponseEntity<List<ProductResponseSummaryDTO>> getUserProducts(@PathVariable Long userId) {
        List<ProductResponseSummaryDTO> products = productService.getProductsByUserId(userId); 
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        } 
        return ResponseEntity.ok(products);
    }

	// Todas las categorias
	@GetMapping("/categories")
	public ResponseEntity<Page<CategoryEntity>> findAllCategoriesPageable(Pageable pageable) {
		return ResponseEntity.ok().body(this.categoryService.findAll(pageable));
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<Page<ProductResponseSummaryDTO>> findAllCategoriesPageable(@PathVariable Long id,
			@AuthenticationPrincipal UserEntity user, Pageable pageable) {
		Page<ProductResponseSummaryDTO> products = (this.productService.findByCategoryNotUser(id, user, pageable))
				.map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
		return ResponseEntity.ok().body(products);
	}

	// Filtrar productos de otros usuarios por category, List<status> - BORRAR
	@GetMapping("/filter")
	public ResponseEntity<Page<ProductResponseSummaryDTO>> filterProducts(
			@RequestParam(required = false) Long categoryId, @RequestParam(required = false) List<Long> status,
			@AuthenticationPrincipal UserEntity user, Pageable pageable) {

		Page<ProductResponseSummaryDTO> products = this.productService
				.filterProducts(categoryId, status, user, pageable)
				.map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
		return ResponseEntity.ok().body(products);
	}

	// Filtrar productos
	@GetMapping("/filter2")
	public ResponseEntity<Page<ProductResponseSummaryDTO>> filterProducts2(
			@RequestParam(required = false) Long categoryId, @RequestParam(required = false) List<Long> status,
			@RequestParam(required = false) BigDecimal minPrice, @RequestParam(required = false) BigDecimal maxPrice,
			@AuthenticationPrincipal UserEntity user, Pageable pageable) {

		Page<ProductResponseSummaryDTO> products = this.productService
				.filterProducts2(categoryId, status, user, minPrice, maxPrice, pageable)
				.map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
		return ResponseEntity.ok().body(products);
	}

	@GetMapping("/status")
	public ResponseEntity<List<StatusEntity>> findAllStatus() {
		return ResponseEntity.ok().body(this.statusService.findAll());
	}

	@PostMapping("/create")
	public ResponseEntity<ProductResponseSummaryDTO> newProduct(
            @NotBlank(message = NAMEP_NOTBLANK_MSG)
            @Size(min = NAMEP_MIN_SIZE, max = NAMEP_MAX_SIZE, message = NAMEP_SIZE_MSG)
            @RequestParam("name") String name,
            @NotBlank(message = DESCRIPTIONP_NOTBLANK_MSG)
            @Size(min = DESCRIPTIONP_MIN_SIZE, max = DESCRIPTIONP_MAX_SIZE, message = DESCRIPTIONP_SIZE_MSG)
            @RequestParam("description") String description,
            @NotNull(message = PRICE_NOT_NULL_MSG)
            @Digits(integer = PRICE_DIGITS_INTEGER, fraction = PRICE_DIGITS_FRACTION, message = PRICE_DIGITS_MSG)
            @DecimalMin(value = PRICE_DECIMAL_MIN, inclusive = PRICE_DECIMAL_MIN_INCLUSIVE, message = PRICE_MIN_MSG)
            @RequestParam("price") BigDecimal price,
            @NotNull(message = STATUSP_MSG)
            @Min(value= STATUSP_MIN, message = STATUSP_MIN_MSG)
            @Digits(integer = STATUSP_DIGITS_INTEGER, fraction = STATUSP_DIGITS_FRACTION, message = STATUS_DIGITS_MSG)
            @RequestParam("status") Long status,
            @NotNull(message = CATEGORY_NOT_NULL)
            @Digits(integer = CATEGORY_DIGITS_INTEGER, fraction = CATEGORY_DIGITS_FRACTION)
            @DecimalMin(value = CATEGORY_DECIMAL_MIN, inclusive = CATEGORY_DECIMAL_MIN_INCLUSIVE)
            @RequestParam("category") Long category,
            @Size(min = PROD_IMG_SIZE_MIN, max = PROD_IMG_SIZE_MAX, message = PROD_IMG_SIZE_MSG)
            @RequestParam("images") MultipartFile[] images,
            @AuthenticationPrincipal UserEntity user) {
		ProductRequestDTO newProduct = ProductRequestDTO.builder().name(name).description(description).price(price)
				.status(status).category(category).urlImage(images).build();
		try {
			ProductResponseSummaryDTO result = productoDTOConverter
					.convertToGetProduct(this.productService.create(newProduct, user), user);
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch (IOException e) {
			throw new ImageStorageException();
		}

	}

//    @PutMapping("/update/{id}")
//    public ResponseEntity<ProductResponseSummaryDTO> updateProduct(@Valid @RequestBody ProductRequestDTO updateProduct,
//                                                                   @AuthenticationPrincipal UserEntity user, @PathVariable Long id) {
//        ProductEntity product = this.productService.edit(updateProduct, user, id);
//        return ResponseEntity.ok().body(productoDTOConverter.convertToGetProduct(product, user));
//    }

	@DeleteMapping("/delete/{id}") public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) { 
		productService.deleteProduct(id, user.getId()); 
		return ResponseEntity.noContent().build(); 
	}

}
