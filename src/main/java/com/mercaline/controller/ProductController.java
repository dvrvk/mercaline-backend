package com.mercaline.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mercaline.dto.ProductRequestDTO;
import com.mercaline.dto.ProductRequestUpdateDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.dto.ProductResponseSummaryDTO;
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

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

import static com.mercaline.config.utils.AppConstants.*;

/**
 * The Class ProductController.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/products")

/**
 * Instantiates a new product controller.
 *
 * @param productService the product service
 * @param productoDTOConverter the producto DTO converter
 * @param categoryService the category service
 * @param statusService the status service
 */
@RequiredArgsConstructor
@Validated
public class ProductController {

    /** The product service. */
    private final ProductService productService;
    
    /** The producto DTO converter. */
    private final ProductoDTOConverter productoDTOConverter;
    
    /** The category service. */
    private final CategoryService categoryService;
    
    /** The status service. */
    private final StatusService statusService;

    /**
     * Find all products.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<Page<ProductResponseSummaryDTO>> findAllProducts(Pageable pageable) {
        Page<ProductResponseSummaryDTO> products = (this.productService.findAll(pageable))
                .map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
        return ResponseEntity.ok().body(products);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body((this.productService.findById(id)).map(this.productoDTOConverter::convertToProductDTO)
                        .orElseThrow(() -> new ProductoNotFoundException(id)));
    }

    /**
     * My products.
     *
     * @param user     the user
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping("/myproducts")
    public ResponseEntity<Page<ProductResponseSummaryDTO>> myProducts(@AuthenticationPrincipal UserEntity user, Pageable pageable) {
        Page<ProductResponseSummaryDTO> myProducts = (this.productService.findByUser(user, pageable))
            .map(product -> productoDTOConverter.convertToGetProduct(product, user));
        return ResponseEntity.ok().body(myProducts);
    }

    /**
     * Find all categories pageable.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryEntity>> findAllCategoriesPageable(Pageable pageable) {
        return ResponseEntity.ok().body(this.categoryService.findAll(pageable));
    }

    /**
     * Find all categories pageable.
     *
     * @param id the id
     * @param user the user
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<Page<ProductResponseSummaryDTO>> findAllCategoriesPageable(@PathVariable Long id,
    		@AuthenticationPrincipal UserEntity user, Pageable pageable) {
        Page<ProductResponseSummaryDTO> products = (this.productService.findByCategoryNotUser(id, user, pageable))
                .map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
        return ResponseEntity.ok().body(products);
    }

    /**
     * Filter products.
     *
     * @param categoryId the category id
     * @param status the status
     * @param minPrice the min price
     * @param maxPrice the max price
     * @param user the user
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponseSummaryDTO>> filterProducts(
            @RequestParam(required = false) Long categoryId, @RequestParam(required = false) List<Long> status,
            @RequestParam(required = false) BigDecimal minPrice, @RequestParam(required = false) BigDecimal maxPrice,
            @AuthenticationPrincipal UserEntity user, Pageable pageable) {
        Page<ProductResponseSummaryDTO> products = this.productService
                .filterProducts2(categoryId, status, user, minPrice, maxPrice, pageable)
                .map(product -> productoDTOConverter.convertToGetProduct(product, product.getUser()));
        return ResponseEntity.ok().body(products);
    }

    /**
     * Find all status.
     *
     * @return the response entity
     */
    @GetMapping("/status")
    public ResponseEntity<List<StatusEntity>> findAllStatus() {
        return ResponseEntity.ok().body(this.statusService.findAll());
    }

    /**
     * Check is mine.
     *
     * @param id the id
     * @param user the user
     * @return the response entity
     */
    @GetMapping("/is-mine/{id}")
    public ResponseEntity<?> checkIsMine(
            @NotNull(message = ID_NOTNULL_MSG)
            @Min(value = ID_MIN, message = ID_MIN_MSG)
            @PathVariable Long id,
            @AuthenticationPrincipal UserEntity user) {
        this.productService.checkIsMine(id, user);
        return ResponseEntity.ok().build();
    }

    /**
     * New product.
     *
     * @param name the name
     * @param description the description
     * @param price the price
     * @param status the status
     * @param category the category
     * @param images the images
     * @param cp the cp
     * @param user the user
     * @return the response entity
     */
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
            @Min(value = STATUSP_MIN, message = STATUSP_MIN_MSG)
            @Digits(integer = STATUSP_DIGITS_INTEGER, fraction = STATUSP_DIGITS_FRACTION, message = STATUS_DIGITS_MSG)
            @RequestParam("status") Long status,
            @NotNull(message = CATEGORY_NOT_NULL)
            @Digits(integer = CATEGORY_DIGITS_INTEGER, fraction = CATEGORY_DIGITS_FRACTION)
            @DecimalMin(value = CATEGORY_DECIMAL_MIN, inclusive = CATEGORY_DECIMAL_MIN_INCLUSIVE)
            @RequestParam("category") Long category,
            @Size(min = PROD_IMG_SIZE_MIN, max = PROD_IMG_SIZE_MAX, message = PROD_IMG_SIZE_MSG)
            @RequestParam("images") MultipartFile[] images,
            @NotNull(message = CP_NOT_NULL)
            @RequestParam("cp") String cp,
            @AuthenticationPrincipal UserEntity user) {
        ProductRequestDTO newProduct = ProductRequestDTO.builder().name(name).description(description).price(price)
                .status(status).category(category).urlImage(images).cp(cp).build();
        try {
            ProductResponseSummaryDTO result = productoDTOConverter
                    .convertToGetProduct(this.productService.create(newProduct, user), user);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IOException e) {
            throw new ImageStorageException();
        }
    }

    /**
     * Update product.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param price the price
     * @param status the status
     * @param category the category
     * @param imageOption the image option
     * @param images the images
     * @param cp the cp
     * @param user the user
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<ProductResponseSummaryDTO> updateProduct(
            @NotNull(message = ID_NOTNULL_MSG)
            @Min(value = ID_MIN, message = ID_MIN_MSG)
            @RequestParam("id") Long id,
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
            @Min(value = STATUSP_MIN, message = STATUSP_MIN_MSG)
            @Digits(integer = STATUSP_DIGITS_INTEGER, fraction = STATUSP_DIGITS_FRACTION, message = STATUS_DIGITS_MSG)
            @RequestParam("status") Long status,
            @NotNull(message = CATEGORY_NOT_NULL)
            @Digits(integer = CATEGORY_DIGITS_INTEGER, fraction = CATEGORY_DIGITS_FRACTION)
            @DecimalMin(value = CATEGORY_DECIMAL_MIN, inclusive = CATEGORY_DECIMAL_MIN_INCLUSIVE)
            @RequestParam("category") Long category,
            @RequestParam("imageOption") String imageOption,
            @RequestParam(value = "images", required = false) MultipartFile[] images,
            @RequestParam(value = "cp", required = false) String cp,
            @AuthenticationPrincipal UserEntity user) {
        ProductResponseSummaryDTO result = productoDTOConverter
                .convertToGetProduct(this.productService.edit(ProductRequestUpdateDTO.builder()
                .id(id)
                .name(name.toUpperCase())
                .description(description)
                .price(price)
                .status(status)
                .category(category)
                .imageOption(imageOption)
                .images(images)
                .user(user)
                .cp(cp)
                .build()), user);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Delete product.
     *
     * @param id the id
     * @param user the user
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        ProductEntity productDelete = this.productService.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        productService.delete(productDelete, user);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark as sold.
     *
     * @param id the id
     * @param user the user
     * @return the response entity
     */
    @PutMapping("/mark-as-sold/{id}")
    public ResponseEntity<?> markAsSold(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        ProductEntity updatedProduct = productService.markAsSold(id, user);
        ProductResponseDTO responseDTO = productoDTOConverter.convertToProductDTO(updatedProduct);
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Mark as available.
     *
     * @param id the id
     * @param user the user
     * @return the response entity
     */
    @PutMapping("/mark-as-available/{id}")
    public ResponseEntity<?> markAsAvailable(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        ProductEntity updatedProduct = productService.markAsAvailable(id, user);
        ProductResponseDTO responseDTO = productoDTOConverter.convertToProductDTO(updatedProduct);
        return ResponseEntity.ok().body(responseDTO);
    }

}
