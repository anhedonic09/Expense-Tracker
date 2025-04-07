package com.expense.tracker.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(nullable = false)
    private String salt_used;
    @Column(nullable = false)
    private String hash_used;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public UserProfile() {}
    public UserProfile(String username, String email, String password, String salt_used, String hash_used, LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt_used = salt_used;
        this.hash_used = hash_used;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt_used() {
        return salt_used;
    }

    public void setSalt_used(String salt_used) {
        this.salt_used = salt_used;
    }

    public String getHash_used() {
        return hash_used;
    }

    public void setHash_used(String hash_used) {
        this.hash_used = hash_used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
