package com.expense.tracker.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUsernameGeneratorUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int USERNAME_LENGTH = 6;

    public static String generateUsername() {
        String randomPart = getRandomString(USERNAME_LENGTH - 2);
        String uniquePart = String.valueOf(System.currentTimeMillis() % 100);
        return randomPart + uniquePart;
    }

    private static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
