package com.mercaline.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.mercaline.dto.ProductRequestDTO;
import com.mercaline.dto.ProductRequestUpdateDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.dto.converter.ProductoDTOConverter;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.CategoryEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.model.StatusEntity;
import com.mercaline.service.CategoryService;
import com.mercaline.service.ProductService;
import com.mercaline.service.StatusService;
import com.mercaline.users.Model.UserEntity;

/**
 * The Class ProductControllerTest.
 */
class ProductControllerTest {

    /** The product service. */
    @Mock
    private ProductService productService;

    /** The producto DTO converter. */
    @Mock
    private ProductoDTOConverter productoDTOConverter;
    
    /** The category service. */
    @Mock
    private CategoryService categoryService;
    
    /** The status service. */
    @Mock
    private StatusService statusService;

    /** The product controller. */
    @InjectMocks
    private ProductController productController;

    /**
     * Sets the up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Find all should return paged products.
     */
    @Test
    void findAll_ShouldReturnPagedProducts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity product = mock(ProductEntity.class);
        UserEntity user = mock(UserEntity.class);
        when(product.getUser()).thenReturn(user);

        ProductResponseSummaryDTO productResponse = new ProductResponseSummaryDTO();
        Page<ProductEntity> productPage = new PageImpl<>(List.of(product));
        Page<ProductResponseSummaryDTO> productResponsePage = new PageImpl<>(List.of(productResponse), pageable, 1);

        when(productService.findAll(pageable)).thenReturn(productPage);
        when(productoDTOConverter.convertToGetProduct(product, user)).thenReturn(productResponse);

        // Act
        ResponseEntity<Page<ProductResponseSummaryDTO>> response = productController.findAllProducts(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponsePage.getContent(), response.getBody().getContent());

        verify(productService, times(1)).findAll(pageable);
        verify(productoDTOConverter, times(1)).convertToGetProduct(product, user);
    }

    /**
     * Find all should return empty page when no products found.
     */
    @Test
    void findAll_ShouldReturnEmptyPageWhenNoProductsFound() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(productService.findAll(pageable)).thenReturn(emptyPage);

        // Act
        ResponseEntity<Page<ProductResponseSummaryDTO>> response = productController.findAllProducts(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getContent().isEmpty());

        verify(productService, times(1)).findAll(pageable);
        verifyNoInteractions(productoDTOConverter);
    }

    /**
     * Find by id should return product response DTO.
     */
    @Test
    void findById_ShouldReturnProductResponseDTO() {
        // Arrange
        Long productId = 1L;
        ProductEntity product = ProductEntity.builder().build(); // Using builder
        ProductResponseDTO productResponse = mock(ProductResponseDTO.class);

        when(productService.findById(productId)).thenReturn(Optional.of(product));
        when(productoDTOConverter.convertToProductDTO(product)).thenReturn(productResponse);

        // Act
        ResponseEntity<ProductResponseDTO> response = productController.findById(productId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());

        verify(productService, times(1)).findById(productId);
        verify(productoDTOConverter, times(1)).convertToProductDTO(product);
    }

    /**
     * Find by id should throw producto not found exception when not found.
     */
    @Test
    void findById_ShouldThrowProductoNotFoundExceptionWhenNotFound() {
        // Arrange
        Long productId = 1L;
        when(productService.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        ProductoNotFoundException exception = assertThrows(ProductoNotFoundException.class, () -> 
            productController.findById(productId));
        assertEquals("No se puede encontrar el producto con el ID: " + productId, exception.getMessage());

        verify(productService, times(1)).findById(productId);
        verifyNoInteractions(productoDTOConverter);
    }
    
    /**
     * Test my products.
     */
    @Test
    public void testMyProducts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos
        ProductEntity product1 = new ProductEntity(); // Asume que tienes una clase Product con sus métodos y atributos
        ProductEntity product2 = new ProductEntity();
        Page<ProductEntity> products = new PageImpl<>(Arrays.asList(product1, product2));

        when(productService.findByUser(user, pageable)).thenReturn(products);
        when(productoDTOConverter.convertToGetProduct(product1, user)).thenReturn(new ProductResponseSummaryDTO());
        when(productoDTOConverter.convertToGetProduct(product2, user)).thenReturn(new ProductResponseSummaryDTO());

        // Act
        ResponseEntity<Page<ProductResponseSummaryDTO>> response = productController.myProducts(user, pageable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }
    
    /**
     * Test find all categories pageable my products.
     */
    @Test
    public void testFindAllCategoriesPageableMyProducts() {
        // Arrange
        Long categoryId = 1L;
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity product1 = new ProductEntity(); // Asume que tienes una clase Product con sus métodos y atributos
        ProductEntity product2 = new ProductEntity();
        Page<ProductEntity> products = new PageImpl<>(Arrays.asList(product1, product2));

        when(productService.findByCategoryNotUser(categoryId, user, pageable)).thenReturn(products);
        when(productoDTOConverter.convertToGetProduct(product1, product1.getUser())).thenReturn(new ProductResponseSummaryDTO());
        when(productoDTOConverter.convertToGetProduct(product2, product2.getUser())).thenReturn(new ProductResponseSummaryDTO());

        // Act
        ResponseEntity<Page<ProductResponseSummaryDTO>> response = productController.findAllCategoriesPageable(categoryId, user, pageable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }
    
    /**
     * Test filter products.
     */
    @Test
    public void testFilterProducts() {
        // Arrange
        Long categoryId = 1L;
        List<Long> status = Arrays.asList(1L, 2L);
        BigDecimal minPrice = new BigDecimal("10.00");
        BigDecimal maxPrice = new BigDecimal("100.00");
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity product1 = new ProductEntity(); // Asume que tienes una clase Product con sus métodos y atributos
        ProductEntity product2 = new ProductEntity();
        Page<ProductEntity> products = new PageImpl<>(Arrays.asList(product1, product2));

        when(productService.filterProducts2(categoryId, status, user, minPrice, maxPrice, pageable)).thenReturn(products);
        when(productoDTOConverter.convertToGetProduct(product1, product1.getUser())).thenReturn(new ProductResponseSummaryDTO());
        when(productoDTOConverter.convertToGetProduct(product2, product2.getUser())).thenReturn(new ProductResponseSummaryDTO());

        // Act
        ResponseEntity<Page<ProductResponseSummaryDTO>> response = productController.filterProducts(
        		categoryId, status, minPrice, maxPrice, user, pageable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }
    
    /**
     * Test mark as sold.
     */
    @Test
    public void testMarkAsSold() {
        // Arrange
        Long productId = 1L;
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos
        ProductEntity updatedProduct = new ProductEntity(); // Asume que tienes una clase ProductEntity con sus métodos y atributos
        ProductResponseDTO expectedResponse = new ProductResponseDTO(); // Asume que tienes una clase ProductResponseDTO con sus métodos y atributos

        when(productService.markAsSold(productId, user)).thenReturn(updatedProduct);
        when(productoDTOConverter.convertToProductDTO(updatedProduct)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> response = productController.markAsSold(productId, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
    }

    /**
     * Test mark as available.
     */
    @Test
    public void testMarkAsAvailable() {
        // Arrange
        Long productId = 1L;
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos
        ProductEntity updatedProduct = new ProductEntity(); // Asume que tienes una clase ProductEntity con sus métodos y atributos
        ProductResponseDTO expectedResponse = new ProductResponseDTO(); // Asume que tienes una clase ProductResponseDTO con sus métodos y atributos

        when(productService.markAsAvailable(productId, user)).thenReturn(updatedProduct);
        when(productoDTOConverter.convertToProductDTO(updatedProduct)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> response = productController.markAsAvailable(productId, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
    }
    
    /**
     * Test find all categories pageable.
     */
    @Test
    public void testFindAllCategoriesPageable() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        CategoryEntity category1 = new CategoryEntity(); // Asume que tienes una clase CategoryEntity con sus métodos y atributos
        CategoryEntity category2 = new CategoryEntity();
        Page<CategoryEntity> categories = new PageImpl<>(Arrays.asList(category1, category2));

        when(categoryService.findAll(pageable)).thenReturn(categories);

        // Act
        ResponseEntity<Page<CategoryEntity>> response = productController.findAllCategoriesPageable(pageable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }
    
    /**
     * Test find all status.
     */
    @Test
    public void testFindAllStatus() {
        // Arrange
        StatusEntity status1 = new StatusEntity(); // Asume que tienes una clase StatusEntity con sus métodos y atributos
        StatusEntity status2 = new StatusEntity();
        List<StatusEntity> statusList = Arrays.asList(status1, status2);

        when(statusService.findAll()).thenReturn(statusList);

        // Act
        ResponseEntity<List<StatusEntity>> response = productController.findAllStatus();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
    
    /**
     * Test check is mine.
     */
    @Test
    public void testCheckIsMine() {
        // Arrange
        Long productId = 1L;
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos

        // Act
        ResponseEntity<?> response = productController.checkIsMine(productId, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Test check is mine not found.
     */
    @Test
    public void testCheckIsMineNotFound() {
        // Arrange
        Long productId = 1L;
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos

        doThrow(new ProductoNotFoundException(productId)).when(productService).checkIsMine(productId, user);

        // Act & Assert
        assertThrows(ProductoNotFoundException.class, () -> {
            productController.checkIsMine(productId, user);
        });
    }
    
    /**
     * Test new product.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testNewProduct() throws IOException {
        // Arrange
        String name = "Test Product";
        String description = "Test Description";
        BigDecimal price = new BigDecimal("99.99");
        Long status = 1L;
        Long category = 1L;
        MockMultipartFile[] images = {}; // Asume que manejas imágenes adecuadamente en tu test
        String cp = "12345";
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos
        user.setId(1L);

        ProductRequestDTO newProduct = ProductRequestDTO.builder()
                .name(name)
                .description(description)
                .price(price)
                .status(status)
                .category(category)
                .urlImage(images)
                .cp(cp)
                .build();

        ProductResponseSummaryDTO expectedResponse = new ProductResponseSummaryDTO(); // Asume que tienes una clase ProductResponseSummaryDTO con sus métodos y atributos
        ProductEntity createdProduct = new ProductEntity();
        
        when(productService.create(newProduct, user)).thenReturn(createdProduct);
        when(productoDTOConverter.convertToGetProduct(createdProduct, user)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ProductResponseSummaryDTO> response = productController.newProduct(
                name, description, price, status, category, images, cp, user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    /**
     * Test update product.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testUpdateProduct() throws IOException {
        // Arrange
        Long id = 1L;
        String name = "Updated Product";
        String description = "Updated Description";
        BigDecimal price = new BigDecimal("199.99");
        Long status = 1L;
        Long category = 1L;
        String imageOption = "keep";
        MockMultipartFile[] images = {}; // Asume que manejas imágenes adecuadamente en tu test
        String cp = "12345";
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos

        ProductRequestUpdateDTO updateProduct = ProductRequestUpdateDTO.builder()
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
                .build();

        // Asume que tienes una clase ProductResponseSummaryDTO con sus métodos y atributos
        ProductResponseSummaryDTO expectedResponse = new ProductResponseSummaryDTO();
        // Simula el producto actualizado
        ProductEntity updatedProduct = new ProductEntity();

        when(productService.edit(updateProduct)).thenReturn(updatedProduct);
        when(productoDTOConverter.convertToGetProduct(updatedProduct, user)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ProductResponseSummaryDTO> response = productController.updateProduct(
                id, name, description, price, status, category, imageOption, images, cp, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    /**
     * Test delete product.
     */
    @Test
    public void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos
        ProductEntity product = new ProductEntity(); // Asume que tienes una clase ProductEntity con sus métodos y atributos

        when(productService.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productService).delete(product, user);

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(productId, user);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Test delete product not found.
     */
    @Test
    public void testDeleteProductNotFound() {
        // Arrange
        Long productId = 1L;
        UserEntity user = new UserEntity(); // Asume que tienes una clase UserEntity con sus métodos y atributos

        when(productService.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductoNotFoundException.class, () -> {
            productController.deleteProduct(productId, user);
        });
    }
}
