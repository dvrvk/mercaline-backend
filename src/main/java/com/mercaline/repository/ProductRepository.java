package com.mercaline.repository;

import com.mercaline.model.CategoryEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.users.Model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * The Interface ProductRepository.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	
    /**
     * Find by user.
     *
     * @param user the user
     * @param pageable the pageable
     * @return the page
     */
    Page<ProductEntity> findByUser(UserEntity user, Pageable pageable);

    /**
     * Find by user not and sold false.
     *
     * @param user the user
     * @param pageable the pageable
     * @return the page
     */
    Page<ProductEntity> findByUserNotAndSoldFalse(UserEntity user, Pageable pageable);

    /**
     * Find by user not and category and sold false.
     *
     * @param user the user
     * @param category the category
     * @param pageable the pageable
     * @return the page
     */
    Page<ProductEntity> findByUserNotAndCategoryAndSoldFalse(UserEntity user, CategoryEntity category, Pageable pageable);

    /**
     * Find products by filter status.
     *
     * @param category the category
     * @param status the status
     * @param userId the user id
     * @param pageable the pageable
     * @return the page
     */
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "(:category IS NULL OR p.category.id = :category) AND " +
            "(p.status.id IN :status) AND "+
            "p.user.id != :userId")
    Page<ProductEntity> findProductsByFilterStatus(@Param("category") Long category,
                                                   @Param("status") List<Long> status,
                                                   @Param("userId") Long userId,
                                                   Pageable pageable);

    /**
     * Find products by filter not status.
     *
     * @param category the category
     * @param userId the user id
     * @param pageable the pageable
     * @return the page
     */
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "(:category IS NULL OR p.category.id = :category) AND " +
            "p.user.id != :userId")
    Page<ProductEntity> findProductsByFilterNotStatus(@Param("category") Long category,
                                                      @Param("userId") Long userId,
                                                      Pageable pageable);

    /**
     * Find products by filter not status 2.
     *
     * @param category the category
     * @param userId the user id
     * @param minPrice the min price
     * @param maxPrice the max price
     * @param pageable the pageable
     * @return the page
     */
    // PRUEBA
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "(:category IS NULL OR p.category.id = :category) AND " +
            "p.user.id != :userId AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "p.sold = false")
    Page<ProductEntity> findProductsByFilterNotStatus2(@Param("category") Long category,
                                                       @Param("userId") Long userId,
                                                       @Param("minPrice") BigDecimal minPrice,
                                                       @Param("maxPrice") BigDecimal maxPrice,
                                                       Pageable pageable);

    /**
     * Find products by filter status 2.
     *
     * @param category the category
     * @param status the status
     * @param userId the user id
     * @param minPrice the min price
     * @param maxPrice the max price
     * @param pageable the pageable
     * @return the page
     */
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "(:category IS NULL OR p.category.id = :category) AND " +
            "(p.status.id IN :status) AND "+
            "p.user.id != :userId AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<ProductEntity> findProductsByFilterStatus2(@Param("category") Long category,
                                                    @Param("status") List<Long> status,
                                                    @Param("userId") Long userId,
                                                    @Param("minPrice") BigDecimal minPrice,
                                                    @Param("maxPrice") BigDecimal maxPrice,
                                                    Pageable pageable);
}
