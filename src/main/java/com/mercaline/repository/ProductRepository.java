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

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

        List<ProductEntity> findByUserId(Long userId);

        Page<ProductEntity> findByUser(UserEntity user, Pageable pageable);

        Page<ProductEntity> findByUserNot(UserEntity user, Pageable pageable);

        Page<ProductEntity> findByUserNotAndCategory(UserEntity user, CategoryEntity category, Pageable pageable);

        @Query("SELECT p FROM ProductEntity p WHERE " +
                        "(:category IS NULL OR p.category.id = :category) AND " +
                        "(p.status.id IN :status) AND " +
                        "p.user.id != :userId")
        Page<ProductEntity> findProductsByFilterStatus(@Param("category") Long category,
                        @Param("status") List<Long> status,
                        @Param("userId") Long userId,
                        Pageable pageable);

        @Query("SELECT p FROM ProductEntity p WHERE " +
                        "(:category IS NULL OR p.category.id = :category) AND " +
                        "p.user.id != :userId")
        Page<ProductEntity> findProductsByFilterNotStatus(@Param("category") Long category,
                        @Param("userId") Long userId,
                        Pageable pageable);

        // PRUEBA
        @Query("SELECT p FROM ProductEntity p WHERE " +
                        "(:category IS NULL OR p.category.id = :category) AND " +
                        "p.user.id != :userId AND " +
                        "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
                        "(:maxPrice IS NULL OR p.price <= :maxPrice)")
        Page<ProductEntity> findProductsByFilterNotStatus2(@Param("category") Long category,
                        @Param("userId") Long userId,
                        @Param("minPrice") BigDecimal minPrice,
                        @Param("maxPrice") BigDecimal maxPrice,
                        Pageable pageable);

        @Query("SELECT p FROM ProductEntity p WHERE " +
                        "(:category IS NULL OR p.category.id = :category) AND " +
                        "(p.status.id IN :status) AND " +
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
