package com.mercaline.dto.converter;

import org.springframework.stereotype.Component;

import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.ResponseUserCompleteDTO;
import com.mercaline.users.dto.ResponseUserSummaryDTO;

/**
 * The Class UserDTOConverter.
 */
@Component
public class UserDTOConverter {
	
    /**
     * Convert to response user summary DTO.
     *
     * @param user the user
     * @return the response user summary DTO
     */
    public ResponseUserSummaryDTO convertToResponseUserSummaryDTO(UserEntity user) {
        return ResponseUserSummaryDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tel(user.getTel())
                .build();
    }

    /**
     * Convert to response user complete DTO.
     *
     * @param user the user
     * @return the response user complete DTO
     */
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
