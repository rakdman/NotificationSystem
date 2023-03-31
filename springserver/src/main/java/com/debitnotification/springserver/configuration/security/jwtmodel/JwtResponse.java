package com.debitnotification.springserver.configuration.security.jwtmodel;

import com.debitnotification.springserver.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private UserRole role;

}
