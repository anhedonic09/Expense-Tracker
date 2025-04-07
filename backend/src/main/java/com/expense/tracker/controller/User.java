package com.expense.tracker.controller;

import com.expense.tracker.dto.CommonResponse;
import com.expense.tracker.entity.UserProfile;
import com.expense.tracker.exception.user.*;
import com.expense.tracker.service.user.UserService;
import com.expense.tracker.utils.PBKDF2Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")

public class User {

    private final UserService userService;

    @Autowired
    public User(UserService userService) {
        this.userService = userService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    @PostMapping("/signup")
    public Map<String, Object> saveUserInfo(@RequestBody UserProfile userProfile){
        LOGGER.info("/signup endpoint triggered...");
        String password = userProfile.getPassword();
        if (StringUtils.hasText(password) && StringUtils.hasText(userProfile.getEmail())) {
            LOGGER.info("fetching existing profile with email if exists....");
            UserProfile existingProfile = userService.findInUserProfileUsingEmail(userProfile.getEmail());
            if (existingProfile == null) {
                return userService.saveUserDetailsToDB(userProfile, password);
            }else{
                LOGGER.info("email already registered....");
                throw new EmailAlreadyExistException("email already registered");
            }
        } else {
            LOGGER.info("mandatory fields can't be empty....");
            throw new InvalidFieldException("mandatory fields can't be empty");
        }
    }

    @PostMapping("/login")
    public Map<String, Object> getUserInfo(@RequestBody UserProfile userProfile) throws Exception {
        LOGGER.info("/login endpoint triggered...");
        String password = userProfile.getPassword();
        if (StringUtils.hasText(password) && StringUtils.hasText(userProfile.getUsername()) && StringUtils.hasText(userProfile.getEmail())){
            LOGGER.info("fetching existing profile with username & email....");
            UserProfile existingProfile = userService.findInUserProfileUsingUsernameAndEmail(userProfile.getUsername(), userProfile.getEmail());
            if (existingProfile != null) {
                return userService.verifyUserDetails(password, existingProfile);
            }else {
                LOGGER.info("no such user found....");
                throw new UserNotFoundException("no such user found");
            }
        }else {
            LOGGER.info("mandatory fields can't be empty....");
            throw new InvalidFieldException("mandatory fields can't be empty");
        }
    }
}
