package com.mercaline.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserSummaryDTO {
    private Long id;
    private String username;
    private String email;
    private String tel;
}
