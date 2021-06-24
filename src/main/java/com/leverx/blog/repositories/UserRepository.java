package com.leverx.blog.repositories;

import com.leverx.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    @Query("select u.id from User u where u.email = :email")
    Optional<Integer> findIdByEmail(@Param("email") String email);
}
