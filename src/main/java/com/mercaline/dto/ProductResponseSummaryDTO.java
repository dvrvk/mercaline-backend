package com.mercaline.dto;

import com.mercaline.users.dto.ResponseUserProductDTO;
import lombok.*;

import java.math.BigDecimal;

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

    private ResponseUserProductDTO seller;
}
