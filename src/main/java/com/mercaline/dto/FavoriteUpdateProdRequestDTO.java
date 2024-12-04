package com.mercaline.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class FavoriteUpdateProdRequestDTO {
    @NotNull
    private Long idList;

    @NotNull
    private Long idProduct;

    @NotNull
    private boolean isDeleteProductList;

    @NotNull
    private boolean isAddProductList;
}
