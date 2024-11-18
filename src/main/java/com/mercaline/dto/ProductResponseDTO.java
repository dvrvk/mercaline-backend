package com.mercaline.dto;

import com.mercaline.users.dto.ResponseUserCompleteDTO;
import com.mercaline.users.dto.ResponseUserSummaryDTO;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long statusId;

    private String status;

    private String imageURL;

    private String category;

    private Long id_category;

    private ResponseUserCompleteDTO seller;
}
