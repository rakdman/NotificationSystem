package com.debitnotification.springserver.configuration.security.jwtmodel;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequest {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
