package com.expense.tracker.utils;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PBKDF2Util {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public String hashPassword(String password, byte[] salt) throws Exception{
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public boolean verifyPassword(String inputPassword, String storedSalt, String storedHash) throws Exception {
        byte[] salt = Base64.getDecoder().decode(storedSalt);
        String newHash = hashPassword(inputPassword, salt);
        return newHash.equals(storedHash);
    }

    public String[] hashPassword(String password) throws Exception {
        byte[] salt = generateSalt();
        String hash = hashPassword(password, salt);
        return new String[]{Base64.getEncoder().encodeToString(salt), hash};
    }
}
