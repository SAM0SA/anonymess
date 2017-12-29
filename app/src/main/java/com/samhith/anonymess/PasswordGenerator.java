package com.samhith.anonymess;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by sam on 12/1/17.
 */

class PasswordGenerator {
    private static final PasswordGenerator ourInstance = new PasswordGenerator();
    private static final String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()-_=+{}[]|';:'?.,<>`~";
    private Random random;

    static PasswordGenerator getInstance() {
        return ourInstance;
    }

    private PasswordGenerator() {
        random = new SecureRandom();
    }

    /**
     * Returns a randomly generated string from charSet of length l
     *
     * @param l the length of the Auth key to be generated
     * */
    public String generatePassword(int l){
        if(l <= 0) {throw new IllegalArgumentException("Auth key length must be a positive integer");}
        StringBuilder keyBuilder = new StringBuilder(l);
        for (int i = 0; i < l; i++) {
            keyBuilder.append(charSet.charAt(random.nextInt(15)));
        }
        return keyBuilder.toString();
    }
}
