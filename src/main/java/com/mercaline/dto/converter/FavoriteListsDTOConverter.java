package com.mercaline.dto.converter;

import org.springframework.stereotype.Component;

import com.mercaline.dto.FavoriteListsResponseDTO;

/**
 * The Class UserDTOConverter.
 */
@Component
public class FavoriteListsDTOConverter {
	
    /**
     * Convert to response user summary DTO.
     *
     * @param user the user
     * @return the response user summary DTO
     */
    public FavoriteListsResponseDTO convertToResponseFavoriteListsSummaryDTO(Long id, String nameList, Integer productSize) {
        return FavoriteListsResponseDTO.builder()
                .id(id)
                .nameList(nameList)
                .productSize(productSize)
                .build();
    }
}
