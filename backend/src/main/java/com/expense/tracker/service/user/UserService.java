package com.expense.tracker.service.user;

import com.expense.tracker.entity.UserProfile;
import com.expense.tracker.exception.user.UserCreationException;

public interface UserService {
    String saveUserDetailsToDB(UserProfile userProfile) throws UserCreationException;
    UserProfile findInUserProfileUsingEmail(String email);
    UserProfile findInUserProfileUsingUsernameAndEmail(String username, String email);

}
