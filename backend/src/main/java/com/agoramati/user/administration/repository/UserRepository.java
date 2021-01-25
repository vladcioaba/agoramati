package com.agoramati.user.administration.repository;

import com.agoramati.user.administration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.userName = ?1 and u.password = ?2")
    public User findUser(String userName, String password);
}