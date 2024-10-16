package com.mercaline.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserCompleteDTO {
    private Long id;

    private String username;

    private String name;

    private String lastname;

    private String email;

    private String tel;
}
