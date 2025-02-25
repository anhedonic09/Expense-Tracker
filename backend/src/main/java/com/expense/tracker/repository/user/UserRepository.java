package com.expense.tracker.repository.user;

import com.expense.tracker.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_profiles (username, email, password, salt_used, hash_used) VALUES (:username, :email, :password, :salt_used, :hash_used)", nativeQuery = true)
    void insertInUserProfile(
            @Param("username") String username,
            @Param("email") String email,
            @Param("password") String password,
            @Param("salt_used") String salt_used,
            @Param("hash_used") String hash_used
    );

    @Query(value = "SELECT * FROM user_profiles WHERE email = :email", nativeQuery = true)
    UserProfile findInUserProfileUsingEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM user_profiles WHERE username = :username AND email = :email", nativeQuery = true)
    UserProfile findInUserProfileUsingUsernameAndEmail(
            @Param("username") String username,
            @Param("email") String email
    );

}
