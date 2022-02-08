package com.example.hackertimebackend.utils;

import com.example.hackertimebackend.db.models.User;
import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtils {

    // Returns SHA3_256(email:password)
    public static String encryptEmailPassword(String email, String password) {
        return DigestUtils.sha3_256Hex(email + ":" + password);
    }

    // Returns SHA3_256(hash:salt)
    public static String encryptFullPassword(String hash, String salt) {
        return DigestUtils.sha3_256Hex(hash + ":" + salt);
    }

    public static boolean validatePassword(User user, String password) {
        return PasswordUtils.encryptFullPassword(PasswordUtils.encryptEmailPassword(user.getEmail(), password),
                user.getSalt()).equals(user.getPassword());
    }
}
