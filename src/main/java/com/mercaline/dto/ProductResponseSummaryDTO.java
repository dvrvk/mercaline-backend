package com.mercaline.dto;

import com.mercaline.users.dto.ResponseUserProductDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseSummaryDTO {

    private Long id;

    private String name;

    private String description;

    private String status;

    private String imageUrl;

    private BigDecimal price;

    private String category;

    private LocalDateTime createDate;

    private ResponseUserProductDTO seller;

    private String cp;

    private boolean sold;
}
