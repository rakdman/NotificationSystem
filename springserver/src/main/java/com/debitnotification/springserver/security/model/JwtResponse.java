package com.debitnotification.springserver.security.model;

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
