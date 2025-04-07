package com.expense.tracker.service.user;

import com.expense.tracker.entity.UserProfile;
import com.expense.tracker.exception.user.UserCreationException;

import java.util.Map;

public interface UserService {
    Map<String, Object> saveUserDetailsToDB(UserProfile userProfile, String password) throws UserCreationException;
    UserProfile findInUserProfileUsingEmail(String email);
    UserProfile findInUserProfileUsingUsername(String username);
    UserProfile findInUserProfileUsingUsernameAndEmail(String username, String email);
    Map<String, Object> verifyUserDetails(String password, UserProfile existingProfile) throws Exception;
}
