package com.expense.tracker.service.user;


import com.expense.tracker.controller.User;
import com.expense.tracker.dto.CommonResponse;
import com.expense.tracker.entity.UserProfile;
import com.expense.tracker.exception.user.PasswordMismatchException;
import com.expense.tracker.exception.user.UserCreationException;
import com.expense.tracker.exception.user.UserNotFoundException;
import com.expense.tracker.repository.user.UserRepository;
import com.expense.tracker.utils.PBKDF2Util;
import com.expense.tracker.utils.RandomUsernameGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService{


    private final RandomUsernameGeneratorUtil usernameGeneratorUtil;
    private final UserRepository userRepository;
    private final PBKDF2Util pbkdf2Util;
    private final CommonResponse commonResponse;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(RandomUsernameGeneratorUtil usernameGeneratorUtil, UserRepository userRepository, CommonResponse commonResponse, PBKDF2Util pbkdf2Util){
        this.usernameGeneratorUtil = usernameGeneratorUtil;
        this.userRepository = userRepository;
        this.pbkdf2Util = pbkdf2Util;
        this.commonResponse = commonResponse;
    }

    @Override
    public Map<String, Object> saveUserDetailsToDB(UserProfile userProfile, String password) throws UserCreationException {
        LOGGER.info("generating username for email {}", userProfile.getEmail());
        String username = usernameGeneratorUtil.generateUsername();
        try {
            LOGGER.info("hashing password....");
            String[] hashPasswordDetails = pbkdf2Util.hashPassword(password);
            String saltUsed = hashPasswordDetails[0];
            String hashUsed = hashPasswordDetails[1];
            userProfile.setPassword(pbkdf2Util.hashPassword(password, pbkdf2Util.generateSalt()));
            userProfile.setHash_used(hashUsed);
            userProfile.setSalt_used(saltUsed);
            LOGGER.info("saving profile....");
            userProfile.setUsername(username);
            userRepository.insertInUserProfile(username, userProfile.getEmail(), userProfile.getPassword(), userProfile.getSalt_used(), userProfile.getHash_used());
            return commonResponse.responseOnSuccess(username, HttpStatus.OK.value(), "user saved successfully");
        }catch (Exception e) {
            throw new UserCreationException("error while creating user profile");
        }
    }

    @Override
    public UserProfile findInUserProfileUsingEmail(String email) {
        try {
            LOGGER.info("fetching user profile with email {}", email);
            UserProfile userProfile = userRepository.findInUserProfileUsingEmail(email);
            return userProfile;
        }catch (RuntimeException e) {
            throw new UserNotFoundException("error while fetching user profile");
        }
    }
    @Override
    public UserProfile findInUserProfileUsingUsername(String username) {
        try {
            LOGGER.info("fetching user profile with username {}", username);
            UserProfile userProfile = userRepository.findInUserProfileUsingUsername(username);
            return userProfile;
        }catch (RuntimeException e) {
            throw new UserNotFoundException("error while fetching user profile");
        }
    }

    @Override
    public UserProfile findInUserProfileUsingUsernameAndEmail(String username, String email) {
        try {
            LOGGER.info("fetching user profile with username {}, email {}", username, email);
            UserProfile userProfile = userRepository.findInUserProfileUsingUsernameAndEmail(username, email);
            return userProfile;
        }catch (RuntimeException e) {
            throw new UserNotFoundException("error while fetching user profile");
        }
    }

    @Override
    public Map<String, Object> verifyUserDetails(String password, UserProfile existingProfile) throws Exception {
        LOGGER.info("hashing password....");
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
    }
}
