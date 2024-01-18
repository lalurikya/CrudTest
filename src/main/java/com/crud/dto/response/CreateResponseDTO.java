package com.crud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateResponseDTO {
    private Boolean status;
    private String message;
    private UserCreateResponseDTO reference_data;
}
