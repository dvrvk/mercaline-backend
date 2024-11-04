package com.mercaline.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.mercaline.config.utils.AppConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUserDTO {

    @NotBlank(message = USERNAME_NOTBLANK_MSG)
    @Size(min = USERNAME_MIN_SIZE, max=USERNAME_MAX_SIZE, message = USERNAME_ERRORSIZE_MSG)
    @Pattern(regexp = USERNAME_REGEXP, message = USERNAME_REGEXP_MSG)
    private String username;

    @NotBlank(message = PASSWORD_NOTBLANK_MSG)
    @Size(min = PASSWORD_MIN_SIZE, message = PASSWORD_MIN_SIZE_MSG)
    private String password;

}
