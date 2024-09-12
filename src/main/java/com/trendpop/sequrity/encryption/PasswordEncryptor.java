package com.trendpop.sequrity.encryption;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
