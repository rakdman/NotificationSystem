package com.debitnotification.springserver.security.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class JwtRequest {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
