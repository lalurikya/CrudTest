package com.crud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResponseDTO {
    private Boolean status;
    private String message;
    private UserUpdateResponseDTO reference_data;
}
