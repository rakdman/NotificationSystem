package com.debitnotification.springserver.configuration.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    public User findByUserName(String userName);

}
