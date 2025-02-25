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

    @Autowired
    PBKDF2Util pbkdf2Util;

    @Autowired
    UserService userService;

    @Autowired
    CommonResponse commonResponse;

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    @PostMapping("/signup")
    public Map<String, Object> saveUserInfo(@RequestBody UserProfile userProfile) throws Exception {
        LOGGER.info("/signup endpoint triggered...");
        String password = userProfile.getPassword();
        if (StringUtils.hasText(password) && StringUtils.hasText(userProfile.getEmail())) {
            LOGGER.info("fetching existing profile with email if exists....");
            UserProfile existingProfile = userService.findInUserProfileUsingEmail(userProfile.getEmail());
            if (existingProfile == null) {
                String[] hashPasswordDetails = pbkdf2Util.hashPassword(password);
                String saltUsed = hashPasswordDetails[0];
                String hashUsed = hashPasswordDetails[1];
                userProfile.setPassword(pbkdf2Util.hashPassword(password, pbkdf2Util.generateSalt()));
                userProfile.setHash_used(hashUsed);
                userProfile.setSalt_used(saltUsed);
                LOGGER.info("saving profile....");
                String uniqueUsername = userService.saveUserDetailsToDB(userProfile);
                return commonResponse.responseOnSuccess(uniqueUsername, HttpStatus.OK.value(), "user saved successfully");
            }else{
                LOGGER.info("email already registered....");
                throw new EmailAlreadyExistException("email already registered");
            }
        } else {
            LOGGER.info("mandatory fields can't be empty....");
            throw new InvalidFieldException("mandatory fields can't be empty");
        }
    }

    @GetMapping("/login")
    public Map<String, Object> getUserInfo(@RequestBody UserProfile userProfile) throws Exception {
        LOGGER.info("/login endpoint triggered...");
        String password = userProfile.getPassword();
        if (StringUtils.hasText(password) && StringUtils.hasText(userProfile.getUsername()) && StringUtils.hasText(userProfile.getEmail())){
            LOGGER.info("fetching existing profile with username & email....");
            UserProfile existingProfile = userService.findInUserProfileUsingUsernameAndEmail(userProfile.getUsername(), userProfile.getEmail());
            if (existingProfile != null) {
                String[] hashPasswordDetails = pbkdf2Util.hashPassword(password);
                String saltUsed = hashPasswordDetails[0];
                String hashUsed = hashPasswordDetails[1];
                if (Boolean.TRUE.equals(
                        pbkdf2Util.verifyPassword(password, saltUsed, hashUsed))
                ) {
                    existingProfile.setPassword("");
                    existingProfile.setHash_used("");
                    existingProfile.setSalt_used("");
                    return commonResponse.responseOnSuccess(existingProfile, HttpStatus.OK.value(), "data fetch successfully");
                } else {
                    LOGGER.info("password not match....");
                    throw new PasswordMismatchException("password not match");
                }
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
