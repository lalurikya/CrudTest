package com.crud.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessLoginDTO {
    private Boolean status;
    private String messages;
    private String access_token;
    private String refresh_token;
}
