package com.mercaline.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {
    private String username;
    private String email;
    private String tel;
}
