package com.mercaline.dto.converter;

import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.ResponseUserCompleteDTO;
import com.mercaline.users.dto.ResponseUserSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {
    public ResponseUserSummaryDTO convertToResponseUserSummaryDTO(UserEntity user) {
        return ResponseUserSummaryDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tel(user.getTel())
                .build();
    }

    public ResponseUserCompleteDTO convertToResponseUserCompleteDTO(UserEntity user) {
        return ResponseUserCompleteDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .tel(user.getTel())
                .build();
    }
}
