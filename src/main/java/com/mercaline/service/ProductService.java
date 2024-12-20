package com.mercaline.service;

import static com.mercaline.config.utils.AppConstants.PATH_IMG;
import static com.mercaline.config.utils.AppConstants.UPDATE_IMAGES_OPTIONS;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mercaline.dto.ProductRequestDTO;
import com.mercaline.dto.ProductRequestUpdateDTO;
import com.mercaline.error.exceptions.CategoryNotFoundException;
import com.mercaline.error.exceptions.IllegalOptionException;
import com.mercaline.error.exceptions.ImageStorageException;
import com.mercaline.error.exceptions.ProductOwnershipException;
import com.mercaline.error.exceptions.ProductUnauthorizedAccessException;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.error.exceptions.StatusNotFoundException;
import com.mercaline.model.CategoryEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.model.StatusEntity;
import com.mercaline.repository.ProductRepository;
import com.mercaline.service.base.BaseService;
import com.mercaline.users.Model.UserEntity;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

/**
 * The Class ProductService.
 */
@Service

/**
 * Instantiates a new product service.
 *
 * @param productRepository the product repository
 * @param categoryService the category service
 * @param statusService the status service
 */
@RequiredArgsConstructor
public class ProductService extends BaseService<ProductEntity, Long, ProductRepository> {

	/** The product repository. */
	private final ProductRepository productRepository;
	
	/** The category service. */
	private final CategoryService categoryService;
	
	/** The status service. */
	private final StatusService statusService;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	/**
	 * Creates the.
	 *
	 * @param newProduct the new product
	 * @param user the user
	 * @return the product entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Transactional(rollbackFor = { IOException.class, RuntimeException.class })
	public ProductEntity create(ProductRequestDTO newProduct, UserEntity user) throws IOException {
		// Buscar si existe category
		CategoryEntity category = categoryService.findById(newProduct.getCategory())
				.orElseThrow(CategoryNotFoundException::new);
		// Buscar si existe status
		StatusEntity status = statusService.findById(newProduct.getStatus()).orElseThrow(StatusNotFoundException::new);

		// Guardar imagen en el servidor
		String imagesPath = saveImages(newProduct.getUrlImage(), user);

		// Construir productEntity
		ProductEntity product = ProductEntity.builder().name(newProduct.getName())
				.description(newProduct.getDescription()).price(newProduct.getPrice()).urlImage(imagesPath)
				.status(status).category(category).user(user).cp(newProduct.getCp()).build();

		return this.repositorio.save(product);
	}

	/**
	 * Delete.
	 *
	 * @param product the product
	 * @param user the user
	 */
	public void delete(ProductEntity product, UserEntity user) {
		ProductEntity existProduct = comprobarPermisosProduct(product, user);
		deleteImages(existProduct.getUrlImage());
		this.repositorio.deleteById(existProduct.getId());
	}

	/**
	 * Edits the.
	 *
	 * @param productUpdate the product update
	 * @return the product entity
	 */
	public ProductEntity edit(ProductRequestUpdateDTO productUpdate) {
		checkIsMine(productUpdate.getId(), productUpdate.getUser());

		ProductEntity myproduct = this.repositorio.findById(productUpdate.getId())
				.orElseThrow(ProductoNotFoundException::new);

		CategoryEntity category = categoryService.findById(productUpdate.getCategory())
				.orElseThrow(CategoryNotFoundException::new);

		StatusEntity status = statusService.findById(productUpdate.getStatus())
				.orElseThrow(StatusNotFoundException::new);

		myproduct.setName(productUpdate.getName());
		myproduct.setDescription(productUpdate.getDescription());
		myproduct.setPrice(productUpdate.getPrice());
		myproduct.setStatus(status);
		myproduct.setCategory(category);

		Optional.ofNullable(productUpdate.getCp())
				.ifPresent(myproduct::setCp);

		try {
			myproduct.setUrlImage(updateImages(productUpdate, myproduct));
			return this.repositorio.save(myproduct);

		} catch (IOException ex) {
			throw new ImageStorageException();
		}
	}

	/**
	 * Check is mine.
	 *
	 * @param id the id
	 * @param user the user
	 * @return true, if successful
	 */
	public boolean checkIsMine(Long id, UserEntity user) {
		ProductEntity product = this.productRepository.findById(id).orElseThrow(ProductoNotFoundException::new);

		if (Objects.equals(product.getUser().getId(), user.getId())) {
			return true;
		} else {
			throw new ProductOwnershipException();
		}

	}

	/**
	 * Find by category not user.
	 *
	 * @param categoryId the category id
	 * @param user the user
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<ProductEntity> findByCategoryNotUser(Long categoryId, UserEntity user, Pageable pageable) {
		CategoryEntity category = categoryService.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

		return this.repositorio.findByUserNotAndCategory(user, category, pageable);
	}

	/**
	 * Find by user.
	 *
	 * @param user the user
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<ProductEntity> findByUser(UserEntity user, Pageable pageable) {
		return this.repositorio.findByUser(user, pageable);
	}

	/**
	 * Find others.
	 *
	 * @param user the user
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<ProductEntity> findOthers(UserEntity user, Pageable pageable) {
		return this.repositorio.findByUserNot(user, pageable);
	}

	/**
	 * Filter products.
	 *
	 * @param category_id the category id
	 * @param statusList the status list
	 * @param user the user
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<ProductEntity> filterProducts(Long category_id, List<Long> statusList, UserEntity user,
			Pageable pageable) {
		// Buscar la categoria
		if (category_id != null && category_id != 0) {
			categoryService.findById(category_id).orElseThrow(CategoryNotFoundException::new);
		}

		if (statusList != null && !statusList.isEmpty()) {
			statusList.forEach(status -> {
				statusService.findById(status).orElseThrow(StatusNotFoundException::new);
			});
		}
		if (statusList == null || statusList.isEmpty()) {
			return this.repositorio.findProductsByFilterNotStatus(category_id, user.getId(), pageable);
		} else {
			return this.repositorio.findProductsByFilterStatus(category_id, statusList, user.getId(), pageable);
		}
	}

	/**
	 * Filter products 2.
	 *
	 * @param category_id the category id
	 * @param statusList the status list
	 * @param user the user
	 * @param minPrice the min price
	 * @param maxPrice the max price
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<ProductEntity> filterProducts2(Long category_id, List<Long> statusList, UserEntity user,
			BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
		// Buscar la categoria
		if (category_id != null && category_id != 0) {
			categoryService.findById(category_id).orElseThrow(CategoryNotFoundException::new);
		}

		if (statusList != null && !statusList.isEmpty()) {
			statusList.forEach(status -> {
				statusService.findById(status).orElseThrow(StatusNotFoundException::new);
			});
		}

		if (statusList == null || statusList.isEmpty()) {
			return this.repositorio.findProductsByFilterNotStatus2(category_id, user.getId(), minPrice, maxPrice,
					pageable);
		} else {
			return this.repositorio.findProductsByFilterStatus2(category_id, statusList, user.getId(), minPrice,
					maxPrice, pageable);
		}
	}

	/**
	 * Comprobar permisos product.
	 *
	 * @param product the product
	 * @param user the user
	 * @return the product entity
	 */
	private ProductEntity comprobarPermisosProduct(ProductEntity product, UserEntity user) {
		ProductEntity myproduct = this.repositorio.findById(product.getId())
				.orElseThrow(() -> new ProductoNotFoundException(product.getId()));

		if (myproduct.getUser().getId().equals(user.getId())) {
			return myproduct;
		} else {
			throw new ProductUnauthorizedAccessException(product.getId());
		}
	}

	/**
	 * Helper method to save images on the server.
	 *
	 * @param images List of image files to save.
	 * @param user   User to whom the images belong.
	 * @return String with paths of saved images, separated by ';'.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String saveImages(MultipartFile[] images, UserEntity user) throws IOException {
		// Create the directory for the user's images
		Path userImageDir = Paths.get(PATH_IMG.concat("/" + user.getId().toString()));
		if (!Files.exists(userImageDir)) {
			Files.createDirectories(userImageDir);
		}

		// StringBuilder to concatenate image paths
		StringBuilder imagePaths = new StringBuilder();
		// Temporal images save
		List<Path> savedImages = new ArrayList<>();
		try {

			for (MultipartFile image : images) {
				// Generate unique name for each image
				String newFileName = "image_" + user.getUsername() + "_" + System.currentTimeMillis() + ".jpg";
				Path imagePath = userImageDir.resolve(newFileName);

				// Transform and save the image at the specified path
				Thumbnails.of(image.getInputStream()).size(800, 800).outputFormat("jpg").outputQuality(0.75)
						.toFile(imagePath.toFile());

				// Append the path to the StringBuilder and Temporal Array
				savedImages.add(imagePath);
				imagePaths.append(imagePath.toString()).append(";");
			}

		} catch (IOException e) {
			// En caso de error, eliminar imágenes ya guardadas
			for (Path imagePath : savedImages) {
				Files.deleteIfExists(imagePath);
			}
			throw new IOException("Error al guardar las imágenes. Se ha revertido la operación.", e);
		}

		// Remove the last ";" and return concatenated paths
		return imagePaths.length() > 0 ? imagePaths.substring(0, imagePaths.length() - 1) : "";
	}

	/**
	 * Update images.
	 *
	 * @param updateProduct the update product
	 * @param myProduct the my product
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String updateImages(ProductRequestUpdateDTO updateProduct, ProductEntity myProduct) throws IOException {
		String option = updateProduct.getImageOption();

		if (!UPDATE_IMAGES_OPTIONS.contains(option)) {
			throw new IllegalOptionException();
		}

		if (option.equals(UPDATE_IMAGES_OPTIONS.get(0))) {
			return myProduct.getUrlImage();
		} else if (option.equals(UPDATE_IMAGES_OPTIONS.get(1))) {
			// Sustituir
			// Borrar las anteriores
			deleteImages(myProduct.getUrlImage());
			// Guardar las nuevas
			return saveImages(updateProduct.getImages(), updateProduct.getUser());
		} else if (option.equals(UPDATE_IMAGES_OPTIONS.get(2))) {
			// Agregar
			// Guardar las nuevas
			String addedURL = saveImages(updateProduct.getImages(), updateProduct.getUser());
			// Agregar a la URL las nuevas urls
			return myProduct.getUrlImage() + ";" + addedURL;
		}
		return null;
	}

	/**
	 * Delete images.
	 *
	 * @param rutas the rutas
	 * @return true, if successful
	 */
	private boolean deleteImages(String rutas) {
		String[] rutasArray = rutas.split(";");
		boolean atLeastOneDeleted = false;
		for (String ruta : rutasArray) {
			Path path = Paths.get(ruta);
			try {
				Files.deleteIfExists(path);
				atLeastOneDeleted = true;
			} catch (IOException e) {
				logger.warn("Imagen no encontrada o ya borrada: " + ruta + "; Error: " + e.getMessage());
			}
		}
		return atLeastOneDeleted;
	}
}
