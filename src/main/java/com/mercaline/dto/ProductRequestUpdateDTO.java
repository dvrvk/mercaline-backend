package com.mercaline.dto;

import com.mercaline.users.Model.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestUpdateDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long status;
    private Long category;
    private String imageOption;
    private MultipartFile[] images;
    private UserEntity user;
}
