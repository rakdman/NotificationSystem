package com.debitnotification.springserver.configuration.user;

import com.debitnotification.springserver.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(unique = true)
    private String userName;
    @NonNull
    private String password;
    @NonNull
    @Email
    private String email;
    private UserRole role = UserRole.NORMAL;
    private String firstName;
    private String lastName;
    private boolean isApproved = Boolean.FALSE;

}
