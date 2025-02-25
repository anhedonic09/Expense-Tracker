package com.expense.tracker.service.user;


import com.expense.tracker.controller.User;
import com.expense.tracker.entity.UserProfile;
import com.expense.tracker.exception.user.UserCreationException;
import com.expense.tracker.exception.user.UserNotFoundException;
import com.expense.tracker.repository.user.UserRepository;
import com.expense.tracker.utils.RandomUsernameGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    RandomUsernameGeneratorUtil usernameGeneratorUtil;

    @Autowired
    UserRepository userRepository;

    @Override
    public String saveUserDetailsToDB(UserProfile userProfile) throws UserCreationException {
        LOGGER.info("generating username for email {}", userProfile.getEmail());
        String username = usernameGeneratorUtil.generateUsername();
        try {
            userProfile.setUsername(username);
            userRepository.insertInUserProfile(username, userProfile.getEmail(), userProfile.getPassword(), userProfile.getSalt_used(), userProfile.getHash_used());
            return username;
        }catch (RuntimeException e) {
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
    public UserProfile findInUserProfileUsingUsernameAndEmail(String username, String email) {
        try {
            LOGGER.info("fetching user profile with username {}, email {}", username, email);
            UserProfile userProfile = userRepository.findInUserProfileUsingUsernameAndEmail(username, email);
            return userProfile;
        }catch (RuntimeException e) {
            throw new UserNotFoundException("error while fetching user profile");
        }
    }
}
