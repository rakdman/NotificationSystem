package com.debitnotification.springserver.configuration.user;

import com.debitnotification.springserver.configuration.security.jwtmodel.JwtRequest;
import com.debitnotification.springserver.configuration.security.jwtmodel.JwtResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody @Valid JwtRequest jwtRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        JwtResponse response = userService.returnToken(jwtRequest);
        log.info(response.getToken());
        return response;
//        return userService.returnToken(jwtRequest);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAll() {
        List<User> allUsers = userService.getAll();
        return ResponseEntity.ok(allUsers);
    }

    @PatchMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

}
