package com.debitnotification.springserver.configuration.user;

import com.debitnotification.springserver.UserRole;
import com.debitnotification.springserver.configuration.security.jwtutil.JWTUtility;
import com.debitnotification.springserver.configuration.security.jwtmodel.JwtRequest;
import com.debitnotification.springserver.configuration.security.jwtmodel.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final JWTUtility jwtUtility;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<User> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    public void deleteUser(User user) {

        if (Utility.isValueExist(user.getUserName())) {
            user = userRepo.findByUserName(user.getUserName());
            if (user != null) userRepo.delete(user);
        }
    }

    public boolean approveUser(String userName) {
        boolean approvalStatus = false;

        if (Utility.isValueExist(userName)) {
            Optional<User> user = Optional.ofNullable(userRepo.findByUserName(userName));
            user.ifPresent(u -> u.setApproved(true));
        }

        return approvalStatus;
    }

    public JwtResponse returnToken(JwtRequest jwtRequest) {
        UserDetails userDetails = loadUserByUsername(jwtRequest.getUserName());
        String token = jwtUtility.generateToken(userDetails);
        UserRole role = userRepo.findByUserName(jwtRequest.getUserName()).getRole();
        return new JwtResponse(token, role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User updateUser(User user) {
        Optional<User> userFromDb = Optional.ofNullable(userRepo.findByUserName(user.getUserName()));
        if (userFromDb.isPresent()) {
            userFromDb.get().setApproved(user.isApproved());
            userRepo.save(userFromDb.get());
            return userFromDb.get();
        }
        return null;
    }


}
