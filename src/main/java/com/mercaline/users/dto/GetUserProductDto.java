package com.mercaline.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserProductDto {
    private Long id;
    private String username;
}
